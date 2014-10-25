package com.kryptnostic.directory.v1;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.directory.v1.response.PublicKeyEnvelope;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

public interface KeyApi {
    String CONTROLLER   = "/keys";
    String PUBLIC_KEY   = "/public";
    String PRIVATE_KEY  = "/private";
    String KODEX        = "/kodex";
    String DOCUMENT_KEY = "/document";

    public static final class PARAM {
        private PARAM() {}

        public static final String REALM = "/{" + Names.REALM_FIELD + "}";
        public static final String USER  = "/{" + Names.USER_FIELD + "}";
        public static final String ID    = "/{" + Names.ID_FIELD + "}";
    }

    @GET( CONTROLLER + PUBLIC_KEY + PARAM.REALM + PARAM.USER )
    PublicKeyEnvelope getPublicKey( @Path( Names.REALM_FIELD ) String realm, @Path( Names.USER_FIELD ) String username );

    @PUT( CONTROLLER + PUBLIC_KEY )
    BasicResponse<String> setPublicKey(@Body PublicKeyEnvelope publicKey );

    @GET( CONTROLLER + PRIVATE_KEY )
    BlockCiphertext getPrivateKey();

    @PUT( CONTROLLER + PRIVATE_KEY )
    BasicResponse<String> setPrivateKey(@Body BlockCiphertext encryptedPrivateKey );

    @GET( CONTROLLER + KODEX )
    Kodex<String> getKodex();

    @PUT( CONTROLLER + KODEX )
    BasicResponse<String> setKodex( @Body Kodex<String> kodex );

    @GET( CONTROLLER + DOCUMENT_KEY + PARAM.ID )
    BasicResponse<byte[]> getDocumentKey( @Path( Names.ID_FIELD ) String id );
}
