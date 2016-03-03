package com.kryptnostic.v2.storage.models;

import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.sharing.models.ObjectUserKey;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@Immutable
public class LegacyObjectMetadata {

    private final UUID     id;
    private final long     version;

    private final UUID     type;
    private final long     size;
    private final UUID     creator;
    private final DateTime createdTime;
    private final Cypher   cipherMethod;

    @JsonCreator
    public LegacyObjectMetadata(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.VERSION_FIELD ) long version,
            @JsonProperty( Names.SIZE_FIELD ) long size,
            @JsonProperty( Names.TYPE_FIELD ) UUID type,
            @JsonProperty( Names.CREATOR_FIELD ) UUID creator,
            @JsonProperty( Names.CYPHER_FIELD ) Cypher cypher,
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime) {
        this.id = id;
        this.version = version;
        this.type = type;
        this.creator = creator;

        this.createdTime = createdTime;
        this.size = size;
        this.cipherMethod = cypher;
    }

    public static LegacyObjectMetadata newRevision(
            CreateObjectRequest request,
            UUID objectId,
            long version,
            UUID creator ) {
        return new LegacyObjectMetadata(
                objectId,
                version,
                0l,
                request.getType(),
                creator,
                request.getCipherType(),
                DateTime.now() );
    }

    /**
     * This constructor uses the objectId as the ACL Id. As such, this should only be used for root objects
     *
     * @param request
     * @param user
     * @param objectId
     * @return
     */
    public static LegacyObjectMetadata newRootObject( CreateObjectRequest request, UUID user, UUID objectId ) {
        return new LegacyObjectMetadata(
                objectId,
                0l,
                0l,
                request.getType(),
                user,
                request.getCipherType(),
                DateTime.now() );
    }

    /**
     * @return Document identifier
     */
    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    /**
     * @return Version of document
     */
    @JsonProperty( Names.VERSION_FIELD )
    public long getVersion() {
        return version;
    }

    @JsonProperty( Names.CREATOR_FIELD )
    public UUID getCreator() {
        return creator;
    }

    @JsonProperty( Names.CREATED_TIME )
    public DateTime getCreatedTime() {
        return createdTime;
    }

    @JsonProperty( Names.TYPE_FIELD )
    public UUID getType() {
        return type;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public long getSize() {
        return size;
    }

    @JsonProperty( Names.CYPHER_FIELD )
    public Cypher getCipherMethod() {
        return cipherMethod;
    }

    public ObjectUserKey toObjectUserKey( UUID userId ) {
        return new ObjectUserKey( this.id, userId );
    }

    public VersionedObjectUserKey toVersionedObjectUserKey( UUID userId ) {
        return new VersionedObjectUserKey( this.id, userId, this.version );
    }

    public VersionedObjectKey getVersionedObjectKey() {
        return new VersionedObjectKey( id, version );
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( cipherMethod == null ) ? 0 : cipherMethod.hashCode() );
        result = prime * result + ( ( createdTime == null ) ? 0 : createdTime.hashCode() );
        result = prime * result + ( ( creator == null ) ? 0 : creator.hashCode() );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + (int) ( size ^ ( size >>> 32 ) );
        result = prime * result + ( ( type == null ) ? 0 : type.hashCode() );
        result = prime * result + (int) ( version ^ ( version >>> 32 ) );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        LegacyObjectMetadata other = (LegacyObjectMetadata) obj;
        if ( cipherMethod != other.cipherMethod ) return false;
        if ( createdTime == null ) {
            if ( other.createdTime != null ) return false;
        } else if ( !createdTime.equals( other.createdTime ) ) return false;
        if ( creator == null ) {
            if ( other.creator != null ) return false;
        } else if ( !creator.equals( other.creator ) ) return false;
        if ( id == null ) {
            if ( other.id != null ) return false;
        } else if ( !id.equals( other.id ) ) return false;
        if ( size != other.size ) return false;
        if ( type == null ) {
            if ( other.type != null ) return false;
        } else if ( !type.equals( other.type ) ) return false;
        if ( version != other.version ) return false;
        return true;
    }

}
