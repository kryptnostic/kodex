package com.kryptnostic.storage.v1.models.utils;

import com.kryptnostic.SecurityConfigurationTestUtils;

public class AesEncryptableUtilsTests extends SecurityConfigurationTestUtils {
    //
    // public void shortStringChunkTest() throws SecurityConfigurationException, IOException, ClassNotFoundException,
    // InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
    // IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
    // InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
    // resetSecurity();
    // List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString( "cool", loader );
    //
    // Assert.assertEquals( 1, results.size() );
    // Assert.assertTrue( results.get( 0 ).isEncrypted() );
    // Assert.assertEquals( 4, results.get( 0 ).decrypt( loader ).getData().length() );
    // }
    //
    // @Test
    // public void longStringChunkTest() throws SecurityConfigurationException, InvalidKeyException,
    // NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
    // BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidParameterSpecException,
    // SealedKodexException, IOException, SignatureException, Exception {
    // resetSecurity();
    // String[] chunk = { fillCharArray( AesEncryptableUtils.CHUNK_MAX ),
    // fillCharArray( AesEncryptableUtils.CHUNK_MAX ), fillCharArray( AesEncryptableUtils.CHUNK_MAX ),
    // fillCharArray( AesEncryptableUtils.CHUNK_MAX ) };
    //
    // String str = new String( chunk[ 0 ] );
    // List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString( str, loader );
    //
    // Assert.assertEquals( 1, results.size() );
    // Assert.assertEquals( chunk[ 0 ], results.get( 0 ).decrypt( loader ).getData() );
    //
    // str = chunk[ 0 ] + chunk[ 1 ] + chunk[ 2 ] + chunk[ 3 ];
    //
    // results = AesEncryptableUtils.chunkString( str, loader );
    //
    // Assert.assertEquals( chunk.length, results.size() );
    // for ( int i = 0; i < chunk.length; i++ ) {
    // Assert.assertEquals( chunk[ i ], results.get( i ).decrypt( loader ).getData() );
    // }
    //
    // List<VerifiedString> verifiedResults = AesEncryptableUtils.chunkStringWithVerification( str, loader );
    //
    // Assert.assertEquals( chunk.length, verifiedResults.size() );
    // for ( int i = 0; i < chunk.length; i++ ) {
    // Assert.assertEquals( chunk[ i ], verifiedResults.get( i ).getData().decrypt( loader ).getData() );
    // }
    // }
    //
    // @Test
    // public void blockIndexTest() {
    // int characterWindow = 3;
    // int maxBlocks = 10;
    // int chunkSize = 8;
    // Pair<Integer, Integer> zero = AesEncryptableUtils.findBlockIndex( 0, characterWindow, maxBlocks, chunkSize );
    // Assert.assertEquals( 0, zero.getLeft().intValue() );
    // Assert.assertEquals( 0, zero.getRight().intValue() );
    //
    // Pair<Integer, Integer> midFirstChunk = AesEncryptableUtils.findBlockIndex(
    // chunkSize - characterWindow,
    // characterWindow,
    // maxBlocks,
    // chunkSize );
    // Assert.assertEquals( 0, midFirstChunk.getLeft().intValue() );
    // Assert.assertEquals( 1, midFirstChunk.getRight().intValue() );
    //
    // Pair<Integer, Integer> endFirstChunk = AesEncryptableUtils.findBlockIndex(
    // chunkSize - 1,
    // characterWindow,
    // maxBlocks,
    // chunkSize );
    // Assert.assertEquals( 0, endFirstChunk.getLeft().intValue() );
    // Assert.assertEquals( 1, endFirstChunk.getRight().intValue() );
    //
    // Pair<Integer, Integer> secondChunk = AesEncryptableUtils.findBlockIndex(
    // chunkSize + characterWindow,
    // characterWindow,
    // maxBlocks,
    // chunkSize );
    // Assert.assertEquals( 1, secondChunk.getLeft().intValue() );
    // Assert.assertEquals( 1, secondChunk.getRight().intValue() );
    //
    // Pair<Integer, Integer> secondToLastChunk = AesEncryptableUtils.findBlockIndex( chunkSize * ( maxBlocks - 1 )
    // + characterWindow, characterWindow, maxBlocks, chunkSize );
    // Assert.assertEquals( maxBlocks - 1, secondToLastChunk.getLeft().intValue() );
    // Assert.assertEquals( maxBlocks - 1, secondToLastChunk.getRight().intValue() );
    //
    // Pair<Integer, Integer> lastTwoChunks = AesEncryptableUtils.findBlockIndex( chunkSize * ( maxBlocks - 1 )
    // + ( characterWindow * 2 ), characterWindow, maxBlocks, chunkSize );
    // Assert.assertEquals( maxBlocks - 1, lastTwoChunks.getLeft().intValue() );
    // Assert.assertEquals( maxBlocks, lastTwoChunks.getRight().intValue() );
    //
    // Pair<Integer, Integer> lastChunk = AesEncryptableUtils.findBlockIndex(
    // chunkSize * maxBlocks + characterWindow,
    // characterWindow,
    // maxBlocks,
    // chunkSize );
    // Assert.assertEquals( maxBlocks, lastChunk.getLeft().intValue() );
    // Assert.assertEquals( maxBlocks, lastChunk.getRight().intValue() );
    //
    // Pair<Integer, Integer> lastChunkClamped = AesEncryptableUtils.findBlockIndex(
    // chunkSize * ( maxBlocks + 1 ),
    // characterWindow,
    // maxBlocks,
    // chunkSize );
    // Assert.assertEquals( maxBlocks, lastChunkClamped.getLeft().intValue() );
    // Assert.assertEquals( maxBlocks, lastChunkClamped.getRight().intValue() );
    // }
    //
    // private String fillCharArray( int sz ) {
    // char[] str = new char[ sz ];
    //
    // Random r = new Random();
    // char c = (char) ( r.nextInt( 26 ) + 'a' );
    //
    // for ( int i = 0; i < sz; i++ ) {
    // str[ i ] = c;
    // }
    //
    // return new String( str );
    // }
}
