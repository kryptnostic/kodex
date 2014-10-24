package com.kryptnostic.storage.v1.client;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.storage.v1.models.request.QueryHasherPairRequest;

public interface SearchFunctionApi {
    String SEARCH_FUNCTION = "/searchFunction";

    @POST( SEARCH_FUNCTION + "/hasher" )
    BasicResponse<String> setQueryHasherPair( @Body QueryHasherPairRequest request );

    @GET( SEARCH_FUNCTION + "/hasher" )
    BasicResponse<Boolean> hasQueryHasherPair();

    @GET( SEARCH_FUNCTION )
    SimplePolynomialFunction getFunction() throws ResourceNotFoundException;
}
