package com.kryptnostic.kodex.v1.models.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
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

    public static class VerifiedStringBlocks {
        private final String verificationHash;
        private final List<AesEncryptable<String>> strings;

        public VerifiedStringBlocks(String verificationHash, List<AesEncryptable<String>> strings) {
            this.verificationHash = verificationHash;
            this.strings = strings;
        }

        public String getVerificationHash() {
            return verificationHash;
        }

        public List<AesEncryptable<String>> getStrings() {
            return strings;
        }
    }

    /**
     * 
     * @param s
     *            Original string, potentially > 4KB
     * @return Collection of AesEncryptable-wrapped 4KB strings that can be concatenated to form the entire original
     *         string
     */
    public static List<AesEncryptable<String>> chunkString(String documentBody) {
        final int CHUNK_MAX = 4096;
        byte[] bytes = documentBody.getBytes();
        int counter = 0;

        List<AesEncryptable<String>> encryptedChunks = Lists.newArrayList();
        while (counter < bytes.length) {
            final byte[] byteChunk = Arrays.copyOfRange(bytes, counter, counter + CHUNK_MAX);

            String strChunk = StringUtils.newStringUtf8(byteChunk);

            AesEncryptable<String> encryptedChunk = new AesEncryptable<String>(strChunk);
            encryptedChunks.add(encryptedChunk);

            counter += CHUNK_MAX;
        }

        return encryptedChunks;
    }

    public static VerifiedStringBlocks chunkStringWithVerification(String documentBody,
            SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        List<AesEncryptable<String>> encryptableStrings = AesEncryptableUtils.chunkString(documentBody);
        String verify = AesEncryptableUtils.generateVerificationHashFromEncryptables(encryptableStrings,
                securityMapping);

        return new VerifiedStringBlocks(verify, encryptableStrings);
    }

    public static Document createEncryptedDocument(String documentId, String body,
            SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {

        VerifiedStringBlocks processedString = chunkStringWithVerification(body, securityMapping);

        return createEncryptedDocument(documentId, body, processedString.getVerificationHash(),
                processedString.getStrings(), securityMapping);
    }

    public static Document createEncryptedDocument(String documentId, String body, String verify,
            List<AesEncryptable<String>> encryptableStrings, SecurityConfigurationMapping securityMapping)
            throws SecurityConfigurationException, IOException, ClassNotFoundException {
        DocumentBlocks blocks = new DocumentBlocks();

        int index = 0;
        for (AesEncryptable<String> encryptedString : encryptableStrings) {
            DocumentBlock block = new DocumentBlock(encryptedString, verify, encryptableStrings.size(), index);
            blocks.add(block);
            index++;
        }

        return new Document(new DocumentMetadata(documentId, verify), blocks);
    }

    public static String generateVerificationHashFromBlocks(Collection<DocumentBlock> blocks,
            SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        List<AesEncryptable<String>> strs = Lists.newArrayList();

        for (DocumentBlock b : blocks) {
            strs.add(b.getBlock());
        }

        return AesEncryptableUtils.generateVerificationHashFromEncryptables(strs, securityMapping);
    }

    public static String generateVerificationHashFromEncryptables(List<AesEncryptable<String>> encryptableStrings,
            SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        String blockHashes = "";
        for (AesEncryptable<String> decryptedString : encryptableStrings) {
            decryptedString = (AesEncryptable<String>) decryptedString.decrypt(securityMapping);
            blockHashes += hashFunction.hashBytes(decryptedString.getData().getBytes()).toString();
        }

        return hashFunction.hashString(blockHashes, Charsets.UTF_8).toString();
    }

    public static String readBlocks(Collection<DocumentBlock> blocks, SecurityConfigurationMapping mapping) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
