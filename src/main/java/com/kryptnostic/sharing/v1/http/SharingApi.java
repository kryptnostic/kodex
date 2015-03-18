package com.kryptnostic.sharing.v1.http;

import java.util.Set;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.models.IncomingShares;
import com.kryptnostic.sharing.v1.models.request.KeyRegistrationRequest;
import com.kryptnostic.sharing.v1.models.request.RevocationRequest;
import com.kryptnostic.sharing.v1.models.request.SharingRequest;
import com.kryptnostic.sharing.v1.models.response.KeyUpdateResponse;
import com.kryptnostic.storage.v1.models.EncryptedSearchObjectKey;

public interface SharingApi {
    String SHARE       = "/share";
    String OBJECT      = "/object";
    String KEYS        = "/keys";
    String OBJECT_KEYS = "/objectKeys";

    @GET( SHARE + OBJECT )
    IncomingShares getIncomingShares();

    @POST( SHARE + OBJECT + "/{id}" )
    BasicResponse<String> removeIncomingShares( @Path( "id" ) String uuid );

    @POST( SHARE + OBJECT )
    BasicResponse<String> share( @Body SharingRequest request );

    @DELETE( SHARE + OBJECT )
    BasicResponse<String> revokeAccess( @Body RevocationRequest request );

    @POST( SHARE + KEYS )
    KeyUpdateResponse registerKeys( @Body KeyRegistrationRequest request );

    @PUT( SHARE + KEYS )
    KeyUpdateResponse registerKeys( @Body Set<EncryptedSearchObjectKey> request );

    @DELETE( SHARE + KEYS )
    KeyUpdateResponse removeKeys( @Body Set<String> uuids );

    @GET( SHARE + OBJECT + "/{id}" + OBJECT_KEYS )
    EncryptedSearchObjectKey getEncryptedSearchObjectKey( @Path( "id" ) String id ) throws ResourceNotFoundException;
}
