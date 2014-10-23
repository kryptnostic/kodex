package com.kryptnostic.sharing.v1;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.requests.KeyRegistrationRequest;

public interface SharingApi {
    String CONTROLLER     = "/share";
    String SHARE_DOCUMENT = "/document";
    String KEY = "/key";
    
    @GET(SHARE_DOCUMENT)
    IncomingShares getIncomingShares();
    
    @POST(KEY)
    BasicResponse<String> registerKey(@Body KeyRegistrationRequest request);
    
    @DELETE(KEY)
    BasicResponse<String> removeKey( @Body DocumentId id );
    
    @POST(SHARE_DOCUMENT)
    BasicResponse<String> shareDocument(@Body SharingRequest request);

    @DELETE(SHARE_DOCUMENT)
    BasicResponse<String> revokeAccess(@Body RevocationRequest request);
}
