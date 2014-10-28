package com.kryptnostic.storage.v1.client;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.storage.v1.models.request.QueryHasherPairRequest;

public interface SearchFunctionApi {
    String CONTROLLER = "/searchFunction";
    
    String HASHER = "/hasher";
    String CHECKSUM = "/checksum";
    
    @POST( CONTROLLER + HASHER )
    BasicResponse<String> setQueryHasherPair( @Body QueryHasherPairRequest request );

    @GET( CONTROLLER + HASHER )
    BasicResponse<Boolean> hasQueryHasherPair();

    @GET( CONTROLLER )
    SimplePolynomialFunction getFunction() throws ResourceNotFoundException;
    
    @GET( CONTROLLER + CHECKSUM )
    BasicResponse<String> getGlobalHasherChecksum();
    
    @GET( CONTROLLER +HASHER )
    BasicResponse<String> getQueryHasherChecksum();
}
