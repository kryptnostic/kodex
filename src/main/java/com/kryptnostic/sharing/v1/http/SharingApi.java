package com.kryptnostic.sharing.v1.http;

import java.util.Set;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.models.DocumentId;
import com.kryptnostic.sharing.v1.models.IncomingShares;
import com.kryptnostic.sharing.v1.models.request.KeyRegistrationRequest;
import com.kryptnostic.sharing.v1.models.request.RevocationRequest;
import com.kryptnostic.sharing.v1.models.request.SharingRequest;
import com.kryptnostic.sharing.v1.models.response.KeyUpdateResponse;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

public interface SharingApi {
    String SHARE    = "/share";
    String DOCUMENT = "/document";
    String KEYS     = "/keys";

    @GET( SHARE + DOCUMENT )
    IncomingShares getIncomingShares();

    @POST( SHARE + DOCUMENT )
    BasicResponse<String> shareDocument( @Body SharingRequest request );

    @DELETE( SHARE + DOCUMENT )
    BasicResponse<String> revokeAccess( @Body RevocationRequest request );

    @POST( SHARE + KEYS )
    KeyUpdateResponse registerKeys( @Body KeyRegistrationRequest request );

    @PUT( SHARE + KEYS )
    KeyUpdateResponse registerKeys( @Body Set<EncryptedSearchDocumentKey> request );

    @DELETE( SHARE + KEYS )
    KeyUpdateResponse removeKeys( @Body Set<DocumentId> ids );
}
