package com.kryptnostic.v2.storage.api;

import java.util.Map;
import java.util.UUID;

import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.v2.storage.models.Scope;

import retrofit.http.GET;
import retrofit.http.Path;

public interface TypesApi {
    String CONTROLLER = "/types";

    String SCOPE      = "scope";
    String SCOPE_PATH = "/{" + SCOPE + "}";

    String TYPE       = Names.TYPE_FIELD;
    String TYPE_PATH  = "/{" + TYPE + "}";

    /**
     * Used to retrieve type information for all available scopes.
     * 
     * @return A mapping of each scope to a mapping of type names to corresponding {@link UUID}s.
     */
    @GET( "/" )
    Map<String, Scope> getScopes();

    /**
     * Used to retrieve type information for a particular scope.
     * 
     * @return A mapping of type names to corresponding {@link UUID}s.
     */
    @GET( SCOPE_PATH )
    Scope getScopeInformation( @Path( SCOPE ) String scope);

    /**
     * Resolves as scope and type name to a UUID, creating one if necessary.
     * 
     * @param scope The name of the scope for which to resolve the type.
     * @param type The name of the type.
     * @return A type UUID that is unique across all scopes.
     */
    @GET( SCOPE_PATH + TYPE_PATH )
    UUID getOrCreateUUIDForType( @Path( SCOPE ) String scope, @Path( TYPE ) String type);

}