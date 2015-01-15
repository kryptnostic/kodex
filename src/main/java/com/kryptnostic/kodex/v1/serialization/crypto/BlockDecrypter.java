package com.kryptnostic.kodex.v1.serialization.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.EncryptableBlock;

public final class BlockDecrypter implements Function<EncryptableBlock, byte[]> {
    private static final Logger logger = LoggerFactory.getLogger( BlockDecrypter.class );
    private final CryptoService service;

    public BlockDecrypter( CryptoService service ) {
        this.service = service;
    }

    @Override
    public byte[] apply( EncryptableBlock input ) {
        try {
            return service.decryptBytes( input.getBlock() );
        } catch ( SecurityConfigurationException e ) {
            logger.error( "Unable to decrypt block {}.", input.getIndex() );
            return null;
        }
    }

}