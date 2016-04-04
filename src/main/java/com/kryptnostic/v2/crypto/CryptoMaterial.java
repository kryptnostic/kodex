package com.kryptnostic.v2.crypto;

import java.io.DataInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.EnumSet;

import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;

public enum CryptoMaterial {
    IV, TAG, CONTENTS, SALT;

    public static EnumSet<CryptoMaterial> requiredByCypher( Cypher cypher ) {
        if ( Cypher.NONE.equals( cypher ) ) {
            return EnumSet.noneOf( CryptoMaterial.class );
        }
        EnumSet<CryptoMaterial> required = EnumSet.of( IV, CONTENTS );
        if ( Cypher.AES_GCM_128.equals( cypher ) || Cypher.AES_GCM_128_SALTED.equals( cypher ) ) {
            required.add( TAG );
        }
        if ( cypher != null && cypher.isSalted() ) {
            required.add( SALT );
        }
        return required;
    }

    public static void serializeEnumSet( ObjectOutput out, EnumSet<CryptoMaterial> set ) throws IOException {
        out.writeBoolean( set.contains( CryptoMaterial.IV ) );
        out.writeBoolean( set.contains( CryptoMaterial.CONTENTS ) );
        out.writeBoolean( set.contains( CryptoMaterial.SALT ) );
        out.writeBoolean( set.contains( CryptoMaterial.TAG ) );
    }

    public static EnumSet<CryptoMaterial> deserializeToEnumSet( DataInput in ) throws IOException {
        EnumSet<CryptoMaterial> mats = EnumSet.<CryptoMaterial> noneOf( CryptoMaterial.class );
        if ( in.readBoolean() ) {
            mats.add( CryptoMaterial.IV );
        }
        if ( in.readBoolean() ) {
            mats.add( CryptoMaterial.CONTENTS );
        }
        if ( in.readBoolean() ) {
            mats.add( CryptoMaterial.SALT );
        }
        if ( in.readBoolean() ) {
            mats.add( CryptoMaterial.TAG );
        }
        return mats;
    }
}