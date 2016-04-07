package com.kryptnostic.directory.v1;

import java.util.UUID;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;

public interface DirectoryClient {
    Iterable<UUID> listUsersInRealm( String realm ) throws BadRequestException;

    byte[] getPublicKey( UUID id );
}
