package com.kryptnostic.directory.v1.principal.utility;

import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.directory.v1.principal.UserKey;

public class UserKeys {

    public static UserKey fromUser( User user ) {
        return new UserKey( user.getRealm(), user.getName() );
    }
}
