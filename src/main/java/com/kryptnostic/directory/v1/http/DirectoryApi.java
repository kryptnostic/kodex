package com.kryptnostic.directory.v1.http;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.model.ByteArrayEnvelope;
import com.kryptnostic.directory.v1.model.response.PublicKeyEnvelope;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
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
    String SALT_KEY         = "/salt";
    String RESOLUTION_KEY   = "/resolve";
    String INITIALIZED      = "/initialized";

    public static final class PARAM {
        private PARAM() {}

        public static final String REALM          = "/{" + Names.REALM_FIELD + "}";
        public static final String USER           = "/{" + Names.USER_FIELD + "}";
        public static final String ID             = "/{" + Names.ID_FIELD + "}";
        public static final String USER_WITH_DOT  = "/{" + Names.USER_FIELD + ":.+}";
        public static final String REALM_WITH_DOT = "/{" + Names.REALM_FIELD + ":.+}";
    }

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi#getRSAPublicKey(UUID user ) } instead
     * @param id The id of the user whose public key shall be retrieved.
     * @return Specified user's public key
     * @throws ResourceNotFoundException
     */
    @Deprecated
    @Timed
    @GET( CONTROLLER + PUBLIC_KEY + PARAM.USER )
    PublicKeyEnvelope getPublicKey( @Path( Names.USER_FIELD ) UUID id ) throws ResourceNotFoundException;

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi#getRSAPublicKeys(Set userIds ) } instead
     * @param userIds The id of the user whose public key shall be retrieved.
     * @return Specified user's public key
     * @throws ResourceNotFoundException
     */
    @Deprecated
    @Timed
    @POST( CONTROLLER + PUBLIC_KEY )
    Map<UUID, PublicKeyEnvelope> getPublicKeys( @Body Set<UUID> userIds )
            throws ResourceNotFoundException;

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi#setRSAPublicKey(byte[] publicKey ) } instead
     * @param publicKey
     * @return
     */
    @Deprecated
    @Timed
    @PUT( CONTROLLER + PUBLIC_KEY )
    BasicResponse<String> setPublicKey( @Body PublicKeyEnvelope publicKey );

    /**
     * Retrieves the password encrypted salt for authentication from the server.
     *
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @param id The id of the user whose encrypted salt shall be retrieved.
     * @return A ciphertext of the password encrypted for the user.
     */
    @Deprecated
    @Timed
    @GET( CONTROLLER + SALT_KEY + PARAM.USER )
    BlockCiphertext getSalt( @Path( Names.USER_FIELD ) UUID id ) throws ResourceNotFoundException;

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @param encryptedSalt
     * @return
     */
    @Deprecated
    @Timed
    @PUT( CONTROLLER + SALT_KEY )
    BasicResponse<String> setSalt( @Body BlockCiphertext encryptedSalt );

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @return Encrypted private key of current user
     */
    @Deprecated
    @Timed
    @GET( CONTROLLER + PRIVATE_KEY )
    BlockCiphertext getPrivateKey() throws ResourceNotFoundException;

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @param encryptedPrivateKey
     * @return
     */
    @Deprecated
    @Timed
    @PUT( CONTROLLER + PRIVATE_KEY )
    BasicResponse<String> setPrivateKey( @Body BlockCiphertext encryptedPrivateKey );

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @param objectId
     * @return
     */
    @Deprecated
    @Timed
    @GET( CONTROLLER + OBJECT_KEY + PARAM.ID )
    BasicResponse<byte[]> getObjectCryptoService( @Path( Names.ID_FIELD ) String objectId );

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @param objectIds
     * @return
     */
    @Deprecated
    @Timed
    @POST( CONTROLLER + OBJECT_KEY )
    Map<String, byte[]> getObjectCryptoServices( @Body Set<String> objectIds );

    /**
     * @deprecated use {@link com.kryptnostic.v2.storage.api.KeyStorageApi } instead
     * @param objectId
     * @param cryptoService
     * @return
     */
    @Deprecated
    @Timed
    @POST( CONTROLLER + OBJECT_KEY + PARAM.ID )
    BasicResponse<String> setObjectCryptoService(
            @Path( Names.ID_FIELD ) String objectId,
            @Body ByteArrayEnvelope cryptoService );

    @Deprecated
    @GET( CONTROLLER + NOTIFICATION_KEY )
    BasicResponse<NotificationPreference> getNotificationPreference();

    @Deprecated
    @PUT( CONTROLLER + NOTIFICATION_KEY )
    BasicResponse<String> setNotificationPreference( @Body NotificationPreference preferences );

    @Timed
    @GET( CONTROLLER + PARAM.REALM )
    Iterable<UUID> listUserInRealm( @Path( Names.REALM_FIELD ) String realm );

    @GET( CONTROLLER + INITIALIZED + PARAM.REALM )
    Iterable<UUID> listInitializedUserInRealm( @Path( Names.REALM_FIELD ) String realm );

    @Timed
    @GET( CONTROLLER + PARAM.REALM + PARAM.USER )
    Optional<UUID> getUUIDFromEmail( @Path( Names.USER_FIELD ) String email );

}
