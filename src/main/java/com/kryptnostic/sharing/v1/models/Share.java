package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.models.request.SharingRequest;

/**
 * Represents the information involved in securely sharing an object from on user to another.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class Share implements Serializable {
    private static final long serialVersionUID = 1145823070022684715L;
    private final String      objectId;
    private final byte[]      encryptedSharingKey;
    private final byte[]      seal;
    private DateTime          creationTime;

    public Share(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey,
            @JsonProperty( Names.PASSWORD_FIELD ) byte[] seal,
            @JsonProperty( Names.TIME_FIELD ) DateTime createdTime ) {
        this.objectId = objectId;
        this.encryptedSharingKey = encryptedSharingKey;
        this.seal = seal;
        this.creationTime = createdTime;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public byte[] getEncryptedSharingKey() {
        return encryptedSharingKey;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public byte[] getSeal() {
        return seal;
    }

    @JsonProperty( Names.TIME_FIELD )
    public DateTime getCreationTime() {
        return creationTime;
    }

    public static Share fromSharingRequest( UserKey user, SharingRequest request ) {
        Share share = new Share( request.getObjectId(), request.getEncryptedSharingKey(), request.getUserKeys().get(
                user ), DateTime.now() );
        return share;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectId == null ) ? 0 : objectId.hashCode() );
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
        if ( objectId == null ) {
            if ( other.objectId != null ) {
                return false;
            }
        } else if ( !objectId.equals( other.objectId ) ) {
            return false;
        }
        return true;
    }
}
