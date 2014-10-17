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

public class AesEncryptableUtils {

    private static final HashFunction hashFunction = Hashing.sha256();
    
    /**
     * 
     * @param s Original string, potentially > 4KB
     * @return Collection of AesEncryptable-wrapped 4KB strings that can be concatenated to form the entire original string
     */
    public static List<AesEncryptable<String>> chunkString(String s) {
        final int CHUNK_MAX = 4096;
        byte[] bytes = s.getBytes();
        int counter = 0;
        
        List<AesEncryptable<String>> encryptedChunks = Lists.newArrayList();
        while(counter < bytes.length) {
            final byte[] byteChunk = Arrays.copyOfRange(bytes, counter, counter + CHUNK_MAX);
            
            String strChunk = StringUtils.newStringUtf8(byteChunk);
            
            AesEncryptable<String> encryptedChunk = new AesEncryptable<String>(strChunk);
            encryptedChunks.add(encryptedChunk);
                    
            counter += CHUNK_MAX;
        }
        
        return encryptedChunks;
    }
    
    
    public static Document createEncryptedDocument(String id, String body, SecurityConfigurationMapping securityMapping)
            throws SecurityConfigurationException, IOException, ClassNotFoundException {
        List<AesEncryptable<String>> strs = AesEncryptableUtils.chunkString(body);
        List<DocumentBlock> blocks = Lists.newArrayList();
        
        String verify = AesEncryptableUtils.generateVerificationHashFromEncryptables(strs, securityMapping);
        
        int index = 0;
        for (AesEncryptable<String> encryptedString : strs) {
            DocumentBlock block = new DocumentBlock(encryptedString, verify, strs.size(), index);
            blocks.add(block);
            index++;
        }
        
        return new Document(id, blocks, verify);
    }

    public static String generateVerificationHashFromBlocks(Collection<DocumentBlock> blocks, SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException, ClassNotFoundException {
        List<AesEncryptable<String>> strs = Lists.newArrayList();
        
        for (DocumentBlock b : blocks) {
            strs.add(b.getBlock());
        }
        
        return AesEncryptableUtils.generateVerificationHashFromEncryptables(strs, securityMapping);
    }

    public static String generateVerificationHashFromEncryptables(List<AesEncryptable<String>> strs, SecurityConfigurationMapping securityMapping) throws SecurityConfigurationException, IOException, ClassNotFoundException {
        String blockHashes = "";
        for (AesEncryptable<String> decryptedString : strs) {
            decryptedString = (AesEncryptable<String>) decryptedString.decrypt(securityMapping);
            blockHashes += hashFunction.hashBytes(decryptedString.getData().getBytes()).toString();
        }
        
        return hashFunction.hashString(blockHashes, Charsets.UTF_8).toString();
    }
}
