package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.model.DomainUpdate;

/**
 * {@code Domain} management functionality.
 * 
 * @author Nick Hewitt
 *
 */
public interface DomainManagementApi {
    public static final String DOMAIN        = "/domain";
    public static final String NAME          = "name";
    public static final String NAME_PATH     = "/{" + NAME + "}";
    public static final String NAME_WITH_DOT = "/{" + NAME + ":.+}";

    /**
     * Update domain settings.
     * 
     * @param request
     * @return
     */
    @Timed
    @POST( DOMAIN )
    Response updateDomain( @Body DomainUpdate request );

    @Timed
    @GET( DOMAIN + NAME_PATH )
    Optional<UUID> getDomainId( @Path( NAME ) String domainName );

}
