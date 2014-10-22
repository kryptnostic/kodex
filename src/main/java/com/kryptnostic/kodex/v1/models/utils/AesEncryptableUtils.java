package com.kryptnostic.kodex.v1.models.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.DocumentBlock;
import com.kryptnostic.storage.v1.models.DocumentBlocks;
import com.kryptnostic.storage.v1.models.DocumentMetadata;

public class AesEncryptableUtils {

    private static final HashFunction hashFunction = Hashing.sha256();

    public static final int CHUNK_MAX = 4096;

    public static class VerifiedStringBlocks {
        private final Map<String, AesEncryptable<String>> strings;

        public VerifiedStringBlocks(Map<String, AesEncryptable<String>> strings) {
            this.strings = strings;
        }

        public Collection<AesEncryptable<String>> getStrings() {
            return strings.values();
        }

        public Map<String, AesEncryptable<String>> getVerifiedStrings() {
            return strings;
        }
    }

    /**
     * 
     * @param s
     *            Original string, potentially > 4KB
     * @return Collection of AesEncryptable-wrapped 4KB strings that can be concatenated to form the entire original
     *         string
     * @throws SecurityConfigurationException
     * @throws JsonProcessingException
     */
    public static List<AesEncryptable<String>> chunkString(String documentBody,
            SecurityConfigurationMapping securityMapping) throws JsonProcessingException,
            SecurityConfigurationException {
        byte[] bytes = documentBody.getBytes();
        int bytesLeft = bytes.length;
        int counter = 0;

        List<AesEncryptable<String>> encryptedChunks = Lists.newArrayList();
        while (bytesLeft > 0) {
            int finish = bytesLeft + CHUNK_MAX;
            if (bytesLeft < CHUNK_MAX) {
                finish = bytesLeft;
            }
            final byte[] byteChunk = Arrays.copyOfRange(bytes, counter, finish);

            String strChunk = StringUtils.newStringUtf8(byteChunk);

            AesEncryptable<String> encryptedChunk = new AesEncryptable<String>(strChunk);
            encryptedChunks.add((AesEncryptable<String>) encryptedChunk.encrypt(securityMapping));

            bytesLeft -= CHUNK_MAX;
            counter += CHUNK_MAX;
        }

        return encryptedChunks;
    }

    public static VerifiedStringBlocks chunkStringWithVerification(String documentBody,
            SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        List<AesEncryptable<String>> encryptableStrings = AesEncryptableUtils
                .chunkString(documentBody, securityMapping);
        return AesEncryptableUtils.generateVerificationHashFromEncryptables(encryptableStrings);
    }

    public static Document createEncryptedDocument(String documentId, String body,
            SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {

        VerifiedStringBlocks verifiedStrings = chunkStringWithVerification(body, securityMapping);

        return createEncryptedDocument(documentId, body, verifiedStrings.getStrings());
    }

    public static Document createEncryptedDocument(String documentId, String body,
            Collection<AesEncryptable<String>> encryptableStrings) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        DocumentBlocks blocks = new DocumentBlocks();

        int index = 0;
        for (AesEncryptable<String> encryptedString : encryptableStrings) {
            DocumentBlock block = new DocumentBlock((AesEncryptable<String>) encryptedString,
                    AesEncryptableUtils.generateVerificationHashForEncryptable(encryptedString),
                    encryptableStrings.size(), index);
            blocks.getBlocks().add(block);
            index++;
        }

        return new Document(new DocumentMetadata(documentId, index), blocks.getBlocks().toArray(new DocumentBlock[0]));
    }

    public static VerifiedStringBlocks generateVerificationHashFromBlocks(Collection<DocumentBlock> blocks)
            throws SecurityConfigurationException, IOException, ClassNotFoundException {
        List<AesEncryptable<String>> strs = Lists.newArrayList();

        for (DocumentBlock b : blocks) {
            strs.add(b.getBlock());
        }

        return AesEncryptableUtils.generateVerificationHashFromEncryptables(strs);
    }

    /**
     * 
     * @param encryptableString
     * @return Verification hash for the encryptable
     * @throws SecurityConfigurationException
     */
    public static String generateVerificationHashForEncryptable(AesEncryptable<String> encryptableString)
            throws SecurityConfigurationException {
        if (!encryptableString.isEncrypted()) {
            throw new SecurityConfigurationException(
                    "Encryptable string must be encrypted, but was found in a decrypted state. Please apply encryption before generating a verification hash.");
        }
        return hashFunction.hashBytes(encryptableString.getEncryptedData().getContents()).toString();
    }

    public static VerifiedStringBlocks generateVerificationHashFromEncryptables(
            List<AesEncryptable<String>> encryptableStrings) throws SecurityConfigurationException {
        Map<String, AesEncryptable<String>> verificationMap = Maps.newHashMap();
        for (AesEncryptable<String> encryptableString : encryptableStrings) {
            verificationMap.put(generateVerificationHashForEncryptable(encryptableString), encryptableString);
        }

        return new VerifiedStringBlocks(verificationMap);
    }

    public static String hashEncryptableBytes(AesEncryptable<?> encryptable) {
        return hashFunction.hashBytes(encryptable.getEncryptedData().getContents()).toString();
    }

    public static String readBlocks(DocumentBlock[] blocks, SecurityConfigurationMapping mapping)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException,
            SecurityConfigurationException {
        String res = "";
        for (DocumentBlock block : blocks) {
            res += block.getBlock().decrypt(mapping).getData();
        }
        return res;
    }
}
