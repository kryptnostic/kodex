package com.kryptnostic.kodex.v1.models.utils;


public class AesEncryptableUtils {

    // private static final HashFunction hashFunction = Hashing.sha256();
    //
    // public static final int CHUNK_MAX = 4096;
    //
    // public static final class VerifiedString {
    // private final String verificationHash;
    // private final AesEncryptable<String> data;
    //
    // public VerifiedString( String verificationHash, AesEncryptable<String> data ) {
    // super();
    // this.verificationHash = verificationHash;
    // this.data = data;
    // }
    //
    // public String getVerificationHash() {
    // return verificationHash;
    // }
    //
    // public AesEncryptable<String> getData() {
    // return data;
    // }
    //
    // public static List<AesEncryptable<String>> getEncryptables( List<VerifiedString> verifiedStrings ) {
    // List<AesEncryptable<String>> encryptables = Lists.newArrayList();
    // for ( VerifiedString v : verifiedStrings ) {
    // encryptables.add( v.getData() );
    // }
    // return encryptables;
    // }
    // }
    //
    // /**
    // *
    // * @param documentBody Original string, potentially &gt; 4KB
    // * @return Collection of AesEncryptable-wrapped (max-length 4096 char) strings that can be concatenated to form
    // the
    // * entire original string
    // * @throws SecurityConfigurationException
    // * @throws JsonProcessingException
    // */
    // public static List<AesEncryptable<String>> chunkString( String documentBody, CryptoServiceLoader loader )
    // throws JsonProcessingException, SecurityConfigurationException {
    // int charsLeft = documentBody.length();
    // int startIndex = 0;
    //
    // List<AesEncryptable<String>> encryptedChunks = Lists.newArrayList();
    // while ( charsLeft > 0 ) {
    // int sizeOfChunk = CHUNK_MAX;
    //
    // // don't overrun for the last chunk
    // if ( charsLeft < CHUNK_MAX ) {
    // sizeOfChunk = charsLeft;
    // }
    //
    // int endIndex = startIndex + sizeOfChunk;
    //
    // final char[] charChunk = new char[ sizeOfChunk ];
    // documentBody.getChars( startIndex, endIndex, charChunk, 0 );
    //
    // String strChunk = new String( charChunk );
    //
    // AesEncryptable<String> encryptedChunk = new AesEncryptable<String>( strChunk );
    // encryptedChunks.add( (AesEncryptable<String>) encryptedChunk.encrypt( loader ) );
    //
    // charsLeft -= CHUNK_MAX;
    // startIndex += CHUNK_MAX;
    // }
    //
    // return encryptedChunks;
    // }
    //
    // public static Document createEncryptedDocument(
    // String documentId,
    // String body,
    // AesEncryptable<String> encryptableString ) throws SecurityConfigurationException, IOException,
    // ClassNotFoundException {
    // Preconditions.checkArgument( encryptableString.isEncrypted() );
    // DocumentBlock[] blocks = encryptableString.getEncryptedData();
    // return new Document( new DocumentMetadata( documentId, blocks.length ), blocks );
    // }

    // /**
    // * Returns a range of block indices inclusive
    // *
    // * Takes into account a characterWindow (radius) to find which blocks hold the data at the specified character
    // * offset +/- the characterWindow
    // *
    // * Takes into account the size of chunks and the max blocks for a document
    // *
    // * @param offset The character offset to start the search
    // * @param characterWindow The radius of characters desired
    // * @param maxBlocks Total blocks in the document, so this won't return any blocks past this
    // * @param chunkSize The max size of each string in each block
    // * @return
    // */
    // public static Pair<Integer, Integer> findBlockIndex(
    // Integer offset,
    // int characterWindow,
    // int maxBlocks,
    // int chunkSize ) {
    // int min = offset - characterWindow;
    // int max = offset + characterWindow;
    //
    // int firstBlock = (int) Math.floor( (double) min / (double) chunkSize );
    // int lastBlock = (int) Math.floor( (double) max / (double) chunkSize );
    //
    // if ( firstBlock < 0 ) {
    // firstBlock = 0;
    // }
    // if ( lastBlock > maxBlocks ) {
    // lastBlock = maxBlocks;
    // }
    // return Pair.<Integer, Integer> of( firstBlock, lastBlock );
    // }
}
