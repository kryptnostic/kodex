package com.kryptnostic.sharing.v1.http;

import java.util.Map;
import java.util.Set;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.indexing.v1.ObjectSearchPair;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.models.IncomingShares;
import com.kryptnostic.sharing.v1.models.request.RevocationRequest;
import com.kryptnostic.sharing.v1.models.request.SharingRequest;
import com.kryptnostic.sharing.v1.models.response.KeyUpdateResponse;

public interface SharingApi {
    String SHARE       = "/share";
    String OBJECT      = "/object";
    String REVOKE      = "/revoke";
    String KEYS        = "/keys";
    String OBJECT_KEYS = "/objectKeys";
    String ID          = "id";

    @GET( SHARE + OBJECT )
    IncomingShares getIncomingShares();

    @POST( SHARE + OBJECT + "/{" + ID + "}" )
    BasicResponse<String> removeIncomingShares( @Path( ID ) String uuid );

    @POST( SHARE + OBJECT + SHARE )
    BasicResponse<String> share( @Body SharingRequest request );

    @POST( SHARE + OBJECT + REVOKE )
    BasicResponse<String> revokeAccess( @Body RevocationRequest request );

    // TODO: Consider creating objects here.
    @PUT( SHARE + KEYS )
    KeyUpdateResponse addSearchPairs( @Body Map<String, ObjectSearchPair> indexPairs );

    @DELETE( SHARE + KEYS )
    KeyUpdateResponse removeKeys( @Body Set<String> uuids );

    @GET( SHARE + OBJECT + "/{" + ID + "}" + OBJECT_KEYS )
    byte[] getSearchPair( @Path( ID ) String id );

}
