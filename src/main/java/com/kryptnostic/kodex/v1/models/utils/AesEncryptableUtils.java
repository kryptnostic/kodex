package com.kryptnostic.kodex.v1.models.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.DocumentBlock;
import com.kryptnostic.storage.v1.models.DocumentBlocks;
import com.kryptnostic.storage.v1.models.DocumentMetadata;

public class AesEncryptableUtils {

    private static final HashFunction hashFunction = Hashing.sha256();

    public static final int           CHUNK_MAX    = 4096;

    public static final class VerifiedString {
        private final String                 verificationHash;
        private final AesEncryptable<String> data;

        public VerifiedString( String verificationHash, AesEncryptable<String> data ) {
            super();
            this.verificationHash = verificationHash;
            this.data = data;
        }

        public String getVerificationHash() {
            return verificationHash;
        }

        public AesEncryptable<String> getData() {
            return data;
        }

        public static List<AesEncryptable<String>> getEncryptables( List<VerifiedString> verifiedStrings ) {
            List<AesEncryptable<String>> encryptables = Lists.newArrayList();
            for ( VerifiedString v : verifiedStrings ) {
                encryptables.add( v.getData() );
            }
            return encryptables;
        }
    }

    /**
     * 
     * @param documentBody Original string, potentially &gt; 4KB
     * @return Collection of AesEncryptable-wrapped (max-length 4096 char) strings that can be concatenated to form the
     *         entire original string
     * @throws SecurityConfigurationException
     * @throws JsonProcessingException
     */
    public static List<AesEncryptable<String>> chunkString( String documentBody, Kodex<String> kodex )
            throws JsonProcessingException, SecurityConfigurationException {
        int charsLeft = documentBody.length();
        int startIndex = 0;

        List<AesEncryptable<String>> encryptedChunks = Lists.newArrayList();
        while ( charsLeft > 0 ) {
            int sizeOfChunk = CHUNK_MAX;

            // don't overrun for the last chunk
            if ( charsLeft < CHUNK_MAX ) {
                sizeOfChunk = charsLeft;
            }

            int endIndex = startIndex + sizeOfChunk;

            final char[] charChunk = new char[ sizeOfChunk ];
            documentBody.getChars( startIndex, endIndex, charChunk, 0 );

            String strChunk = new String( charChunk );

            AesEncryptable<String> encryptedChunk = new AesEncryptable<String>( strChunk );
            encryptedChunks.add( (AesEncryptable<String>) encryptedChunk.encrypt( kodex ) );

            charsLeft -= CHUNK_MAX;
            startIndex += CHUNK_MAX;
        }

        return encryptedChunks;
    }

    public static List<VerifiedString> chunkStringWithVerification( String documentBody, Kodex<String> kodex )
            throws SecurityConfigurationException, IOException, ClassNotFoundException {
        List<AesEncryptable<String>> encryptableStrings = AesEncryptableUtils.chunkString( documentBody, kodex );
        return AesEncryptableUtils.generateVerificationHashFromEncryptables( encryptableStrings );
    }

    public static Document createEncryptedDocument( String documentId, String body, Kodex<String> kodex )
            throws SecurityConfigurationException, IOException, ClassNotFoundException {

        List<VerifiedString> verifiedStrings = chunkStringWithVerification( body, kodex );
        return createEncryptedDocument( documentId, body, VerifiedString.getEncryptables( verifiedStrings ) );
    }

    public static Document createEncryptedDocument(
            String documentId,
            String body,
            Collection<AesEncryptable<String>> encryptableStrings ) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        DocumentBlocks blocks = new DocumentBlocks();

        int index = 0;
        for ( AesEncryptable<String> encryptedString : encryptableStrings ) {
            DocumentBlock block = new DocumentBlock(
                    encryptedString,
                    AesEncryptableUtils.generateVerificationHashForEncryptable( encryptedString ),
                    encryptableStrings.size(),
                    index );
            blocks.getBlocks().add( block );
            index++;
        }

        return new Document( new DocumentMetadata( documentId, index ), blocks.getBlocks().toArray(
                new DocumentBlock[ 0 ] ) );
    }

    public static List<VerifiedString> generateVerificationHashFromBlocks( Collection<DocumentBlock> blocks )
            throws SecurityConfigurationException, IOException, ClassNotFoundException {
        List<AesEncryptable<String>> strs = Lists.newArrayList();

        for ( DocumentBlock b : blocks ) {
            strs.add( b.getBlock() );
        }

        return AesEncryptableUtils.generateVerificationHashFromEncryptables( strs );
    }

    /**
     * 
     * @param encryptableString
     * @return Verification hash for the encryptable
     * @throws SecurityConfigurationException
     */
    public static String generateVerificationHashForEncryptable( AesEncryptable<String> encryptableString )
            throws SecurityConfigurationException {
        if ( !encryptableString.isEncrypted() ) {
            throw new SecurityConfigurationException(
                    "Encryptable string must be encrypted, but was found in a decrypted state. Please apply encryption before generating a verification hash." );
        }
        return hashFunction.hashBytes( encryptableString.getEncryptedData().getContents() ).toString();
    }

    public static List<VerifiedString> generateVerificationHashFromEncryptables(
            List<AesEncryptable<String>> encryptableStrings ) throws SecurityConfigurationException {
        List<VerifiedString> verifications = Lists.newArrayList();
        for ( AesEncryptable<String> encryptableString : encryptableStrings ) {
            verifications.add( new VerifiedString(
                    generateVerificationHashForEncryptable( encryptableString ),
                    encryptableString ) );
        }

        return verifications;
    }

    public static String hashEncryptableBytes( AesEncryptable<?> encryptable ) {
        return hashFunction.hashBytes( encryptable.getEncryptedData().getContents() ).toString();
    }

    public static String readBlocks( DocumentBlock[] blocks, Kodex<String> kodex ) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException, SecurityConfigurationException {
        String res = "";
        for ( DocumentBlock block : blocks ) {
            res += block.getBlock().decrypt( kodex ).getData();
        }
        return res;
    }

    /**
     * Returns a range of block indices inclusive
     *
     * Takes into account a characterWindow (radius) to find which blocks hold the data at the specified character
     * offset +/- the characterWindow
     * 
     * Takes into account the size of chunks and the max blocks for a document
     * 
     * @param offset The character offset to start the search
     * @param characterWindow The radius of characters desired
     * @param maxBlocks Total blocks in the document, so this won't return any blocks past this
     * @param chunkSize The max size of each string in each block
     * @return
     */
    public static Pair<Integer, Integer> findBlockIndex(
            Integer offset,
            int characterWindow,
            int maxBlocks,
            int chunkSize ) {
        int min = offset - characterWindow;
        int max = offset + characterWindow;

        int firstBlock = (int) Math.floor( (double) min / (double) chunkSize );
        int lastBlock = (int) Math.floor( (double) max / (double) chunkSize );

        if ( firstBlock < 0 ) {
            firstBlock = 0;
        }
        if ( lastBlock > maxBlocks ) {
            lastBlock = maxBlocks;
        }
        return Pair.<Integer, Integer> of( firstBlock, lastBlock );
    }
}
