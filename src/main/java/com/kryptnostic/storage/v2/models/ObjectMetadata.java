package com.kryptnostic.storage.v2.models;

import java.util.EnumSet;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.storage.v2.types.TypeUUIDs;
import com.kryptnostic.v2.constants.Names;

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
@Immutable
public class ObjectMetadata {
    protected final UUID                id;
    protected final UUID                type;
    protected final long                version;
    protected final long                size;
    protected EnumSet<CryptoMaterial>   uploadedParts;

    protected final UUID     creator;
    protected final DateTime createdTime;

    public enum CryptoMaterial {
        IV, TAG, CONTENTS, SALT
    }

    @JsonCreator
    public ObjectMetadata(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.VERSION_FIELD ) long version,
            @JsonProperty( Names.SIZE_FIELD ) long size,
            @JsonProperty( Names.TYPE_FIELD ) UUID type,
            @JsonProperty( Names.CREATOR_FIELD ) UUID creator,
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime ) {
        this.id = id;
        this.version = version;
        this.type = type;

        this.creator = creator;

        this.createdTime = createdTime;
        this.size = size;
    }

    public static ObjectMetadata newObject(
             UUID id,
             UUID creator ) {
        return new ObjectMetadata( id, 0, 0, TypeUUIDs.DEFAULT, creator, DateTime.now() );
    }

    public static ObjectMetadata newObject(
             UUID id,
             UUID creator,
             long version ) {
        return new ObjectMetadata( id, version, 0, TypeUUIDs.DEFAULT, creator, DateTime.now() );
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

    public boolean updateUploadProgress( CryptoMaterial nextUploaded ) {
        uploadedParts.add( nextUploaded );
        return uploadedParts.containsAll( EnumSet.allOf( CryptoMaterial.class ) );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( createdTime == null ) ? 0 : createdTime.hashCode() );
        result = prime * result + ( ( creator == null ) ? 0 : creator.hashCode() );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + (int) ( size ^ ( size >>> 32 ) );
        result = prime * result + ( ( type == null ) ? 0 : type.hashCode() );
        result = prime * result + (int) ( version ^ ( version >>> 32 ) );
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
        if ( !( obj instanceof ObjectMetadata ) ) {
            return false;
        }
        ObjectMetadata other = (ObjectMetadata) obj;
        if ( createdTime == null ) {
            if ( other.createdTime != null ) {
                return false;
            }
        } else if ( !createdTime.equals( other.createdTime ) ) {
            return false;
        }
        if ( creator == null ) {
            if ( other.creator != null ) {
                return false;
            }
        } else if ( !creator.equals( other.creator ) ) {
            return false;
        }
        if ( id == null ) {
            if ( other.id != null ) {
                return false;
            }
        } else if ( !id.equals( other.id ) ) {
            return false;
        }
        if ( size != other.size ) {
            return false;
        }
        if ( type == null ) {
            if ( other.type != null ) {
                return false;
            }
        } else if ( !type.equals( other.type ) ) {
            return false;
        }
        if ( version != other.version ) {
            return false;
        }
        return true;
    }


}
