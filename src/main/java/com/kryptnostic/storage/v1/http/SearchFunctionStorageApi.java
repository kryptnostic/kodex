package com.kryptnostic.storage.v1.http;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.kodex.v1.models.utils.SimplePolynomialFunctionValidator;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.storage.v1.models.request.QueryHasherPairRequest;

public interface SearchFunctionStorageApi {
    String CONTROLLER = "/searchFunction";

    String HASHER = "/hasher";
    String CHECKSUM = "/checksum";
    String VALIDATE = "/validate";

    @POST( CONTROLLER + HASHER )
    BasicResponse<String> setQueryHasherPair( @Body QueryHasherPairRequest request );

    @POST( CONTROLLER + HASHER + VALIDATE)
    BasicResponse<Boolean> validateQueryHasherPair( @Body SimplePolynomialFunctionValidator[] validators );

    @GET( CONTROLLER + HASHER )
    BasicResponse<Boolean> hasQueryHasherPair();

    @GET( CONTROLLER )
    SimplePolynomialFunction getFunction() throws ResourceNotFoundException;

    @GET( CONTROLLER + CHECKSUM )
    BasicResponse<String> getGlobalHasherChecksum();

    @GET( CONTROLLER + HASHER + CHECKSUM  )
    BasicResponse<String> getQueryHasherChecksum();
}
