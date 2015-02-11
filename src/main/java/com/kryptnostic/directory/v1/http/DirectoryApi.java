package com.kryptnostic.directory.v1.http;

import java.util.Map;
import java.util.Set;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.codahale.metrics.annotation.Timed;
import com.kryptnostic.directory.v1.models.ByteArrayEnvelope;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.directory.v1.models.response.PublicKeyEnvelope;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.models.NotificationPreference;

public interface DirectoryApi {
    String CONTROLLER       = "/directory";
    String PUBLIC_KEY       = "/public";
    String PRIVATE_KEY      = "/private";
    String KODEX            = "/kodex";
    String OBJECT_KEY       = "/object";
    String NOTIFICATION_KEY = "/notifications";

    public static final class PARAM {
        private PARAM() {}

        public static final String REALM = "/{" + Names.REALM_FIELD + "}";
        public static final String USER  = "/{" + Names.USER_FIELD + "}";
        public static final String ID    = "/{" + Names.ID_FIELD + "}";
    }

    /**
     * @param username
     * @return Specified user's public key
     */
    @Timed
    @GET( CONTROLLER + PUBLIC_KEY + PARAM.USER )
    PublicKeyEnvelope getPublicKey( @Path( Names.USER_FIELD ) String username );

    @Timed
    @PUT( CONTROLLER + PUBLIC_KEY )
    BasicResponse<String> setPublicKey( @Body PublicKeyEnvelope publicKey );

    /**
     * @return Encrypted private key of current user
     */
    @Timed
    @GET( CONTROLLER + PRIVATE_KEY )
    BlockCiphertext getPrivateKey();

    @Timed
    @PUT( CONTROLLER + PRIVATE_KEY )
    BasicResponse<String> setPrivateKey( @Body BlockCiphertext encryptedPrivateKey );

    @Timed
    @GET( CONTROLLER + KODEX )
    Kodex<String> getKodex();

    @Timed
    @PUT( CONTROLLER + KODEX )
    BasicResponse<String> setKodex( @Body Kodex<String> kodex );

    @Timed
    @GET( CONTROLLER + OBJECT_KEY + PARAM.ID )
    BasicResponse<byte[]> getObjectCryptoService( @Path( Names.ID_FIELD ) String objectId )
            throws ResourceNotFoundException;

    @Timed
    @POST( CONTROLLER + OBJECT_KEY )
    Map<String, byte[]> getObjectCryptoServices( @Body Set<String> objectIds );

    @Timed
    @POST( CONTROLLER + OBJECT_KEY + PARAM.ID )
    BasicResponse<String> setObjectCryptoService(
            @Path( Names.ID_FIELD ) String objectId,
            @Body ByteArrayEnvelope cryptoService );

    @GET( CONTROLLER + NOTIFICATION_KEY )
    BasicResponse<NotificationPreference> getNotificationPreferences();

    @PUT( CONTROLLER + NOTIFICATION_KEY )
    BasicResponse<String> setNotificationPreferences( @Body NotificationPreference preferences );

    @Timed
    @GET( CONTROLLER + PARAM.REALM )
    Set<UserKey> listUserInRealm( @Path( Names.REALM_FIELD ) String realm );
}
