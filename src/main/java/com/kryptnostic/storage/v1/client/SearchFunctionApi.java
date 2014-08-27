package com.kryptnostic.storage.v1.client;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public interface SearchFunctionApi {
    String SEARCH_FUNCTION = "/searchFunction";
    
    @POST(SEARCH_FUNCTION)
    BasicResponse<Boolean> setFunction(@Body SimplePolynomialFunction function);
    
    @GET(SEARCH_FUNCTION)
    BasicResponse<SimplePolynomialFunction> getFunction() throws ResourceNotFoundException;
}
