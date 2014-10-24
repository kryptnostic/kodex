package com.kryptnostic.sharing.v1.requests;

import java.util.List;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.IncomingShares;
import com.kryptnostic.sharing.v1.SharingRequest;
import com.kryptnostic.sharing.v1.models.KeyUpdateResponse;
import com.kryptnostic.sharing.v1.models.PairedEncryptedSearchDocumentKey;
import com.kryptnostic.sharing.v1.models.RevocationRequest;

public interface SharingApi {
    String CONTROLLER     = "/share";
    String SHARE_DOCUMENT = "/document";
    String KEYS           = "/keys";

    @GET( SHARE_DOCUMENT )
    IncomingShares getIncomingShares();

    @POST( SHARE_DOCUMENT )
    BasicResponse<String> shareDocument( @Body SharingRequest request );

    @DELETE( SHARE_DOCUMENT )
    BasicResponse<String> revokeAccess( @Body RevocationRequest request );

    @POST( KEYS )
    KeyUpdateResponse registerKeys( @Body KeyRegistrationRequest request );

    @PUT( KEYS )
    KeyUpdateResponse registerKeys( @Body List<PairedEncryptedSearchDocumentKey> request );
    
    @DELETE( KEYS )
    KeyUpdateResponse removeKeys( @Body List<UUID> ids );
}
