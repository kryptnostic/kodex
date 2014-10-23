package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.ciphers.Cyphers;
import com.kryptnostic.users.v1.UserKey;

public class DocumentKodexTests {
    private static final Logger logger = LoggerFactory.getLogger( DocumentKodexTests.class );
    private static KeyPair keyPair;
    
    @BeforeClass
    public static void genKeys() throws NoSuchAlgorithmException {
        keyPair = Keys.generateRsaKeyPair( 4096 );
    }
    
    @Test
    public void testPutGet() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        testPutGet(new DocumentKodex<String>(Cypher.RSA_OAEP_SHA256_4096), "test" );
    }
    
    @Test
    public void testJacksonSerialization() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        DocumentKodex<UserKey> kodex = new DocumentKodex<UserKey>(Cypher.RSA_OAEP_SHA256_4096);
        
        byte[] secretKey1 = Cyphers.generateSalt();
        byte[] secretKey2 = Cyphers.generateSalt();
        kodex.put( new UserKey("kryptnostic" , "test1" ) , Cyphers.encrypt( kodex.getCypher() , keyPair.getPublic() , secretKey1 ) );
        kodex.put( new UserKey("kryptnostic" , "test2" ) , Cyphers.encrypt( kodex.getCypher() , keyPair.getPublic() , secretKey2 ) );
        
        String serialized = mapper.writeValueAsString( kodex );
        logger.info("DocumentKodex<UserKey> JSON: {}", serialized );
        DocumentKodex<UserKey> actual = mapper.readValue( serialized , new TypeReference<DocumentKodex<UserKey>>(){} ) ;
        
        Assert.assertEquals( kodex , actual );
    }
    
    public static void testPutGet(DocumentKodex<String> kodex, String id ) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] secretKey = Cyphers.generateSalt();
        kodex.put( id , Cyphers.encrypt( kodex.getCypher() , keyPair.getPublic() , secretKey ) );
        byte[] actual = kodex.get( keyPair.getPrivate() , id );
        Assert.assertArrayEquals( secretKey , actual );
    }
    
    public static class DocumentKodexWrapper {
        public DocumentKodex<UserKey> kodex;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( kodex == null ) ? 0 : kodex.hashCode() );
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!( obj instanceof DocumentKodexWrapper )) {
                return false;
            }
            DocumentKodexWrapper other = (DocumentKodexWrapper) obj;
            if (kodex == null) {
                if (other.kodex != null) {
                    return false;
                }
            } else if (!kodex.equals( other.kodex )) {
                return false;
            }
            return true;
        }
        
    }
}
