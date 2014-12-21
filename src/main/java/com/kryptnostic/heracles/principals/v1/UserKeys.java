package com.kryptnostic.heracles.principals.v1;

import com.kryptnostic.directory.v1.models.UserKey;


public class UserKeys {

    public static UserKey fromUser(User user) {
        return new UserKey( user.getRealm(), user.getName() );
    }
}
