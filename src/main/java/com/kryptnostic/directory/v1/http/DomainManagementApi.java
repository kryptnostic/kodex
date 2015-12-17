package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.model.DomainUpdate;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * {@code Domain} management functionality.
 * 
 * @author Nick Hewitt
 * @author Yao Pan
 *
 */
public interface DomainManagementApi {
    public static final String DOMAIN        = "/domain";
    public static final String NAME          = "name";
    public static final String NAME_PATH     = "/{" + NAME + "}";
    public static final String NAME_WITH_DOT = "/{" + NAME + ":.+}";

    public static final String WHITE_LIST    = "/whitelist";

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
    Optional<UUID> getDomainId( @Path( NAME ) String domainName);

    /**
     * Update white list settings.
     * 
     * @param domains
     * @return
     */
    @Timed
    @PUT( WHITE_LIST + NAME_PATH )
    Response addDomainWhiteList( @Path( NAME ) String domainName, @Body String[] domains);

    @Timed
    @GET( WHITE_LIST + NAME_PATH )
    String[] getDomainWhiteList( @Path( NAME ) String domainName);

    @Timed
    @DELETE( WHITE_LIST + NAME_PATH )
    Response deleteDomainWhiteList( @Path( NAME ) String domainName, @Body String[] domains);
}