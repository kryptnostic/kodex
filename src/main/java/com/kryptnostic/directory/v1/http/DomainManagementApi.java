package com.kryptnostic.directory.v1.http;

import retrofit.http.POST;

import com.codahale.metrics.annotation.Timed;
import com.kryptnostic.directory.v1.model.DomainUpdate;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

/**
 * {@code Domain} management functionality.
 * 
 * @author Nick Hewitt
 *
 */
public interface DomainManagementApi {
    String DOMAIN = "/domain";

    /**
     * Update domain settings.
     * 
     * @param request
     * @return
     */
    @Timed
    @POST( DOMAIN )
    BasicResponse<String> updateSharingPolicy( DomainUpdate request );

}
