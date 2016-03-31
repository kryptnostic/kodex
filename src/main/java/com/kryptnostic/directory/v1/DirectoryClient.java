package com.kryptnostic.directory.v1;

import java.util.UUID;

public interface DirectoryClient {
    Iterable<UUID> listUsersInRealm( String realm );

    byte[] getPublicKey( UUID id );
}
