package com.kryptnostic.v2.sharing.models;

import java.util.Arrays;
import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

/**
 * Represents the information involved in securely sharing an object from one user to another.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class Share {

    private final UUID                      sourceUserId;
    private final VersionedObjectKey        objectKey;
    private final Optional<BlockCiphertext> encryptedSharingPair;
    private final byte[]                    publicKeyEncryptedCryptoService;
    private final DateTime                  creationTime;

    public Share(
            @JsonProperty( Names.USER_FIELD ) UUID sourceUserId,
            @JsonProperty( Names.ID_FIELD ) VersionedObjectKey objectKey,
            @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD ) Optional<BlockCiphertext> encryptedObjectSharingPair,
            @JsonProperty( Names.WRAPPED_CRYPTO_SERVICE ) byte[] publicKeyEncryptedCryptoService,
            @JsonProperty( Names.TIME_FIELD ) DateTime createdTime) {
        this.sourceUserId = sourceUserId;
        this.objectKey = objectKey;
        this.encryptedSharingPair = encryptedObjectSharingPair;
        this.publicKeyEncryptedCryptoService = publicKeyEncryptedCryptoService;
        this.creationTime = createdTime;
    }

    @JsonProperty( Names.ID_FIELD )
    public VersionedObjectKey getObjectKey() {
        return objectKey;
    }

    @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD )
    public Optional<BlockCiphertext> getEncryptedSharingPair() {
        return encryptedSharingPair;
    }

    @JsonProperty( Names.WRAPPED_CRYPTO_SERVICE )
    public byte[] getPublicKeyEncryptedCryptoService() {
        return publicKeyEncryptedCryptoService;
    }

    @JsonProperty( Names.TIME_FIELD )
    public DateTime getCreationTime() {
        return creationTime;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getSourceUserId() {
        return sourceUserId;
    }

    public static Share fromSharingRequest( UUID sourceUser, UUID user, SharingRequest request ) {
        Share share = new Share(
                sourceUser,
                request.getObjectKey(),
                request.getEncryptedSharingPair(),
                request.getUserKeys().get(
                        user ),
                DateTime.now() );
        return share;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( creationTime == null ) ? 0 : creationTime.hashCode() );
        result = prime * result + ( ( encryptedSharingPair == null ) ? 0 : encryptedSharingPair.hashCode() );
        result = prime * result + ( ( objectKey == null ) ? 0 : objectKey.hashCode() );
        result = prime * result + Arrays.hashCode( publicKeyEncryptedCryptoService );
        result = prime * result + ( ( sourceUserId == null ) ? 0 : sourceUserId.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof Share ) ) {
            return false;
        }
        Share other = (Share) obj;
        if ( creationTime == null ) {
            if ( other.creationTime != null ) {
                return false;
            }
        } else if ( !creationTime.equals( other.creationTime ) ) {
            return false;
        }
        if ( encryptedSharingPair == null ) {
            if ( other.encryptedSharingPair != null ) {
                return false;
            }
        } else if ( !encryptedSharingPair.equals( other.encryptedSharingPair ) ) {
            return false;
        }
        if ( objectKey == null ) {
            if ( other.objectKey != null ) {
                return false;
            }
        } else if ( !objectKey.equals( other.objectKey ) ) {
            return false;
        }
        if ( !Arrays.equals( publicKeyEncryptedCryptoService, other.publicKeyEncryptedCryptoService ) ) {
            return false;
        }
        if ( sourceUserId == null ) {
            if ( other.sourceUserId != null ) {
                return false;
            }
        } else if ( !sourceUserId.equals( other.sourceUserId ) ) {
            return false;
        }
        return true;
    }
}
