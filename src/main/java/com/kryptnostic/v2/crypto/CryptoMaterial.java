package com.kryptnostic.v2.crypto;

import java.io.DataInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;

/**
 * BlockCiphertext elements.
 *
 * @author Drew Bailey &lt;drew@kryptnostic.com&gt;
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public enum CryptoMaterial {
    IV,
    TAG,
    CONTENTS,
    SALT;
    private static final int              LENGTH    = CryptoMaterial.values().length;
    private static final CryptoMaterial[] materials = CryptoMaterial.values();
    private static transient Map<Cypher, EnumSet<CryptoMaterial>> cachedReqByCypher = Maps.newConcurrentMap();

    public static EnumSet<CryptoMaterial> requiredByCypher( Cypher cypher ) {
        Preconditions.checkNotNull( cypher, "Cipher cannot be null." );
        EnumSet<CryptoMaterial> required = EnumSet.of( IV, CONTENTS );
        switch ( cypher ) {
            case NONE:
                return EnumSet.noneOf( CryptoMaterial.class );
            case AES_GCM_128:
            case AES_GCM_128_SALTED:
                required.add( TAG );
                break;
            default:
                break;
        }

        if ( cypher.isSalted() ) {
            required.add( SALT );
        }
        cachedReqByCypher.put( cypher, required );
        return required;
    }

    public static void serializeEnumSet( ObjectOutput out, EnumSet<CryptoMaterial> set ) throws IOException {
        BitSet s = new BitSet( LENGTH );
        for ( CryptoMaterial cm : set ) {
            s.set( cm.ordinal() );
        }
        out.write( s.toByteArray() );
    }

    public static EnumSet<CryptoMaterial> deserializeToEnumSet( DataInput in ) throws IOException {
        byte[] b = new byte[ ( LENGTH / Byte.SIZE ) + ( ( LENGTH % Byte.SIZE ) > 0 ? 1 : 0 ) ];
        BitSet s = BitSet.valueOf( b );
        EnumSet<CryptoMaterial> mats = EnumSet.<CryptoMaterial> noneOf( CryptoMaterial.class );
        for ( int i = s.nextSetBit( 0 ); i >= 0; i = s.nextSetBit( i + 1 ) ) {
            mats.add( materials[ i ] );
        }
        return mats;
    }

    public static CryptoMaterial[] getValues() {
        return materials;
    }
}