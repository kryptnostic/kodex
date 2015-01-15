package com.kryptnostic.kodex.v1.crypto.keys;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.CipherDescription;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cyphers;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

/**
 * Class is used to bootstrap a new device, without having to regenerate FHE keys.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class DocumentKodex<T> implements Serializable {
    private static final long    serialVersionUID = -6717541341623629426L;
    private static final String  CYPHER_FIELD     = "cipher";
    private static final String  KEYS_FIELD       = "keys";

    private final Cypher         cypher;
    private final Map<T, byte[]> keys;

    public DocumentKodex( Cypher cypher ) {
        this.cypher = cypher;
        keys = Maps.newHashMap();
    }

    @JsonCreator
    public DocumentKodex(
            @JsonProperty( CYPHER_FIELD ) CipherDescription cypher,
            @JsonProperty( KEYS_FIELD ) Map<T, byte[]> keys ) {
        this( Cypher.createCipher( cypher ) );
        this.keys.putAll( keys );
    }

    @JsonProperty( CYPHER_FIELD )
    public Cypher getCypher() {
        return cypher;
    }

    @JsonProperty( KEYS_FIELD )
    public Map<T, byte[]> getKeys() {
        return keys;
    }

    public byte[] get( T id ) {
        return keys.get( id );
    }

    public byte[] get( PrivateKey key, T id ) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, SecurityConfigurationException {
        return Cyphers.decrypt( cypher, key, keys.get( id ) );
    }

    public void put( T id, byte[] secretKey ) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        keys.put( id, secretKey );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( cypher == null ) ? 0 : cypher.hashCode() );
        result = prime * result + ( ( keys == null ) ? 0 : keys.hashCode() );
        return result;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof DocumentKodex ) ) {
            return false;
        }
        @SuppressWarnings( "rawtypes" )
        DocumentKodex other = (DocumentKodex) obj;
        if ( cypher != other.cypher ) {
            return false;
        }
        if ( keys == null ) {
            if ( other.keys != null ) {
                return false;
            }
        } else if ( !mapEquals( keys, other.keys ) ) {
            return false;
        }

        return true;
    }

    private static boolean mapEquals( Map<?, byte[]> lhs, Map<?, byte[]> rhs ) {
        if ( !lhs.keySet().equals( rhs.keySet() ) ) {
            return false;
        }
        for ( Object key : lhs.keySet() ) {
            if ( !Arrays.equals( lhs.get( key ), rhs.get( key ) ) ) {
                return false;
            }
        }
        return true;
    }
}
