package com.kryptnostic.directory.v1.http;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.domain.DomainPolicy;
import com.kryptnostic.directory.v1.model.DomainUpdate;
import com.kryptnostic.directory.v2.model.DomainResourcesUsage;

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
    public static final String DOMAIN               = "/domain";
    public static final String DOMAIN_NAMES         = "/domainnames";
    public static final String NOTIFICATION_EMAIL   = "/notificationemail";
    public static final String WHITE_LIST           = "/whitelist";
    public static final String DOMAIN_LIST          = "/domainlist";
    public static final String SHARING_POLICY       = "/sharingpolicy";
    public static final String DOMAIN_RESOURCES     = "/domainresources";

    public static final String NAME                 = "name";
    public static final String DOMAIN_ID            = "domainid";
    public static final String RESOURCE             = "resource";
    public static final String NAME_PATH            = "/{" + NAME + "}";
    public static final String NAME_WITH_DOT        = "/{" + NAME + ":.+}";
    public static final String DOMAIN_ID_PATH       = "/{" + DOMAIN_ID + "}";
    public static final String DOMAIN_RESOURCE_PATH = "/{" + RESOURCE + "}";

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
    
    @GET( DOMAIN_NAMES + DOMAIN_ID_PATH )
    String getDomainName( @Path( DOMAIN_ID ) UUID domainUUID );
    
    @GET( DOMAIN_NAMES )
    Map<UUID, String> getDomainNames( @Body Set<UUID> domainUUIDs );

    @GET( DOMAIN + SHARING_POLICY )
    DomainPolicy getDomainSharingPolicy();
    
    @POST( DOMAIN + SHARING_POLICY )
    Response setDomainSharingPolicy( @Body DomainPolicy domainSharingPolicy );

    @GET( DOMAIN + NOTIFICATION_EMAIL )
    String getDomainEamilAddress();

    @POST( DOMAIN + NOTIFICATION_EMAIL )
    Response setDomainEmailAddress( @Body String email );

    /**
     * Update white list settings.
     * 
     * @param domains
     * @return
     */
    @GET( WHITE_LIST )
    Set<String> getDomainWhiteList();
    
    @PUT( WHITE_LIST )
    Response addToDomainWhiteList( @Body Set<String> domainNames);
    
    @DELETE( WHITE_LIST )
    Response removeFromDomainWhiteList( @Body Set<String> domainNames );

    @GET( DOMAIN_LIST )
    Set<String> getListableDomainsForWhiteList();

    @GET( DOMAIN_RESOURCES + DOMAIN_ID_PATH + DOMAIN_RESOURCE_PATH )
    DomainResourcesUsage getDomainResourcesUsage( @Path( DOMAIN_ID ) UUID domainId, @Path( RESOURCE ) String resource );

}