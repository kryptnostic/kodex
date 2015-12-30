package com.kryptnostic.v2.storage.api;

import java.util.Map;
import java.util.UUID;

import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.v2.storage.types.Scope;
import com.kryptnostic.v2.storage.types.ScopeListing;
import com.kryptnostic.v2.storage.types.ScopeRequestOptions;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface TypesApi {
    String SCOPES     = "/types";

    String SCOPE      = "scope";
    String SCOPE_PATH = "/{" + SCOPE + "}";

    String TYPE       = Names.TYPE_FIELD;
    String TYPE_PATH  = "/{" + TYPE + "}";

    @GET( SCOPES )
    Map<String, UUID> getScopes();

    @GET( SCOPES + SCOPE )
    Scope getScopeInformation();

    @GET( SCOPES + SCOPE_PATH + TYPE_PATH )
    UUID getOrCreateUuidForType( @Path( SCOPE ) String scope, @Path( TYPE ) UUID type );

    @POST( SCOPES )
    ScopeListing getAllTypeInformation( @Body ScopeRequestOptions scopeRequest );

}