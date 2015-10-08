package com.kryptnostic.directory.v1.http;

import retrofit.http.POST;

import com.codahale.metrics.annotation.Timed;
import com.kryptnostic.directory.v1.model.request.SharingPolicyUpdateRequest;
import com.kryptnostic.directory.v1.model.request.VisibilityPolicyUpdateRequest;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

/**
 * {@code Domain} administration functionality.
 * 
 * @author Nick Hewitt
 *
 */
public interface AdminApi {
    String CONTROLLER        = "/admin";
    String SHARING_POLICY    = "/sharing";
    String VISIBILITY_POLICY = "/visibility";

    /**
     * Update domain sharing policy.
     * 
     * @param request
     * @return
     */
    @Timed
    @POST( CONTROLLER + SHARING_POLICY )
    BasicResponse<String> updateSharingPolicy( SharingPolicyUpdateRequest request );

    /**
     * Update domain visibility policy.
     */
    @Timed
    @POST( CONTROLLER + VISIBILITY_POLICY )
    BasicResponse<String> updateVisibilityPolicy( VisibilityPolicyUpdateRequest request );
}
