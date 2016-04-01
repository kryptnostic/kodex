package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

import retrofit.http.GET;
import retrofit.http.Path;

public interface DirectoryApi {
    String CONTROLLER       = "/directory";
    String SALT_KEY         = "/salt";
    String RESOLUTION_KEY   = "/resolve";
    String INITIALIZED      = "/initialized";

    public static final class PARAM {
        private PARAM() {}

        private static final String REALM          = "/{" + Names.REALM_FIELD + "}";
        private static final String USER           = "/{" + Names.USER_FIELD + "}";
        public static final String REALM_WITH_DOT = "/{" + Names.REALM_FIELD + ":.+}";
    }

    @Timed
    @GET( CONTROLLER + PARAM.REALM )
    Iterable<UUID> listUserInRealm( @Path( Names.REALM_FIELD ) String realm );

    @GET( CONTROLLER + INITIALIZED + PARAM.REALM )
    Iterable<UUID> listInitializedUserInRealm( @Path( Names.REALM_FIELD ) String realm );

    @GET( CONTROLLER + PARAM.REALM + PARAM.USER )
    Optional<UUID> getUUIDFromEmail( @Path( Names.USER_FIELD ) String email );

}
