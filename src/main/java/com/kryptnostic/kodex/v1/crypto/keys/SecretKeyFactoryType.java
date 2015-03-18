package com.kryptnostic.kodex.v1.crypto.keys;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.SecretKeyFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public enum SecretKeyFactoryType {
    PBKDF2WithHmacSHA1,
    PBKDF2WithHmacSHA256,
    PBKDF2WithHmacSHA384,
    PBKDF2WithHmacSHA512,
    PBEWithHmacSHA256AndAES_128,
    PBEWithHmacSHA384AndAES_128,
    PBEWithHmacSHA512AndAES_128,
    PBEWithHmacSHA256AndAES_256,
    PBEWithHmacSHA384AndAES_256,
    PBEWithHmacSHA512AndAES_256,
    AES;
    
    private static final Map<String, SecretKeyFactoryType> mapper = Maps.newHashMap();
    static {
        for( SecretKeyFactoryType t : SecretKeyFactoryType.values() ) {
            mapper.put( t.name() , t );
        }
    }
    
    @JsonValue
    public String getValue() {
        return name();
    }
    
    public SecretKeyFactory getInstance() throws NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance( name() );
    }
    
    @JsonCreator
    public static SecretKeyFactoryType fromString( String name ) {
        return Preconditions.checkNotNull( mapper.get( name ) , "Unrecognized secret key factory type." );
    }
}
