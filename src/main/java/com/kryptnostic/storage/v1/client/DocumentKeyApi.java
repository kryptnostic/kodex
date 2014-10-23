package com.kryptnostic.storage.v1.client;

import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

public interface DocumentKeyApi {
    String DOCUMENT_KEY = "/documentKey";
    
    @POST(DOCUMENT_KEY)
    BasicResponse<Boolean> addDocumentKeys(@Body Collection<EncryptedSearchDocumentKey> keys);
    
    @GET(DOCUMENT_KEY)
    BasicResponse<Collection<EncryptedSearchDocumentKey>> getDocumentKeys();
}
