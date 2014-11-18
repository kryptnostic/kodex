package com.kryptnostic.directory.v1;

import java.util.Set;

import com.kryptnostic.directory.v1.models.UserKey;

public interface DirectoryClient {
    Set<UserKey> listUserInRealm( String realm );
}
