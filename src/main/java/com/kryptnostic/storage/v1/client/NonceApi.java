package com.kryptnostic.storage.v1.client;

import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import cern.colt.bitvector.BitVector;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;

public interface NonceApi {
    String NONCE = "/nonce";
    
    @POST(NONCE)
    BasicResponse<Boolean> addNonces(@Body Collection<BitVector> nonces);
    
    @GET(NONCE)
    BasicResponse<Collection<BitVector>> getNonces();
}
