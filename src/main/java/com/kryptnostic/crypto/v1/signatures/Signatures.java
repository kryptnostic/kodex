package com.kryptnostic.crypto.v1.signatures;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Signatures {
    private Signatures() {
    }

    private static final Logger logger = LoggerFactory.getLogger( Signatures.class );

    public static Signature createSigner(SignatureAlgorithm algorithm) {
        try {
            return Signature.getInstance( algorithm.toString() );
        } catch (NoSuchAlgorithmException e) {
            logger.error( "Unable to acquire instance of {}" , algorithm );
            return null;
        }
    }

    public static Signature createSigner(
            SignatureAlgorithm preferredAlgorithm,
            SignatureAlgorithm ... alternativeAlgorithms) {

        Signature signature = createSigner( preferredAlgorithm );
        for (int i = 0; ( signature == null ) && ( i < alternativeAlgorithms.length ); ++i) {
            signature = createSigner( alternativeAlgorithms[i] );
        }
        
        return signature;
    }
}
