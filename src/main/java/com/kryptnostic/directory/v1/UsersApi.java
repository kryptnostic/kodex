package com.kryptnostic.directory.v1;

import java.util.Set;

import retrofit.http.GET;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.users.v1.UserKey;

public interface UsersApi {
    String CONTROLLER = "/users";

    public static final class PARAM {
        private PARAM() {}

        public static final String REALM = "/{" + Names.REALM_FIELD + "}";
    }

    @GET( CONTROLLER + PARAM.REALM )
    Set<UserKey> listUserInRealm( @Path( Names.REALM_FIELD ) String realm );
}
