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
    String CONTROLLER = "/keys";
    String PUBLIC_KEY = "/public";
    String PRIVATE_KEY = "/private";
    String KODEX_KEY = "/kodex";
    String DOCUMENT_KEY = "/document";
    
    public static final class PARAM {
        private PARAM() {}
        public static final String REALM = "/{" + Names.REALM_FIELD + "}";
        public static final String USER = "/{" + Names.USER_FIELD + "}";
        public static final String ID = "/{" + Names.ID_FIELD + "}";
    }
    
    @GET( PUBLIC_KEY + PARAM.REALM + PARAM.USER )
    PublicKeyEnvelope getPublicKey( @Path(Names.REALM_FIELD) String realm , @Path( Names.USER_FIELD) String username );
    
    @PUT( PUBLIC_KEY + PARAM.REALM + PARAM.USER )
    BasicResponse<String> setPublicKey( @Path(Names.REALM_FIELD) String realm , @Path( Names.USER_FIELD) String username , @Body PublicKeyEnvelope publicKey );
    
    @GET( PRIVATE_KEY + PARAM.REALM + PARAM.USER )
    BlockCiphertext getPrivateKey( @Path(Names.REALM_FIELD) String realm , @Path( Names.USER_FIELD) String username );
    
    @PUT( PRIVATE_KEY + PARAM.REALM + PARAM.USER )
    BasicResponse<String> setPrivateKey( @Path(Names.REALM_FIELD) String realm , @Path( Names.USER_FIELD) String username , @Body BlockCiphertext encryptedPrivateKey );
    
    @GET( KODEX_KEY + PARAM.REALM + PARAM.USER )
    Kodex<String> getKodex(@Path(Names.REALM_FIELD) String realm , @Path( Names.USER_FIELD) String username);
    
    @PUT( KODEX_KEY + PARAM.REALM + PARAM.USER )
    BasicResponse<String> setKodex(@Path(Names.REALM_FIELD) String realm , @Path( Names.USER_FIELD) String username , @Body Kodex kodex);
    
    @GET( DOCUMENT_KEY + PARAM.ID )
    BasicResponse<byte[]> getDocumentKey(@Path(Names.ID_FIELD) String id);
}

