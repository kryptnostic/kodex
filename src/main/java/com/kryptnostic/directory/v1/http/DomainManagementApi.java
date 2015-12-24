package com.kryptnostic.directory.v1.http;

import java.util.Set;
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
    public static final String DOMAIN         = "/domain";

    public static final String WHITE_LIST     = "/whitelist";

    public static final String DOMAIN_LIST    = "/domainlist";

    public static final String NAME           = "name";
    public static final String NAME_PATH      = "/{" + NAME + "}";
    public static final String NAME_WITH_DOT  = "/{" + NAME + ":.+}";
    public static final String SHARING_POLICY = "/sharingpolicy";

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

    @GET( DOMAIN + SHARING_POLICY )
    String getDomainSharingPolicy();

    /**
     * Update white list settings.
     * 
     * @param domains
     * @return
     */
    @PUT( WHITE_LIST )
    Response addToDomainWhiteList( @Body Set<String> domains );

    @GET( WHITE_LIST )
    Set<String> getDomainWhiteList();

    @DELETE( WHITE_LIST )
    Response removeFromDomainWhiteList( @Body Set<String> domains );

    @GET( DOMAIN_LIST )
    Set<String> getListableDomainsForWhiteList();

}