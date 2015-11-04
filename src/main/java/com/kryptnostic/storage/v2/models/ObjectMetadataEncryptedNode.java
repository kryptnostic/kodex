package com.kryptnostic.storage.v2.models;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

/**
 * This class is a return only JSON data type that used to return nest object tree with objects at various load levels
 * from the server.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class ObjectMetadataEncryptedNode {
    private final Map<UUID, ObjectMetadataEncryptedNode> children;
    private final ObjectMetadata                         metadata;
    private final Optional<BlockCiphertext>              data;

    public ObjectMetadataEncryptedNode( ObjectMetadata metadata ) {
        this( metadata, null );
    }

    public ObjectMetadataEncryptedNode( ObjectMetadata metadata, BlockCiphertext ciphertext ) {
        this.children = Maps.newHashMap();
        this.metadata = metadata;
        this.data = Optional.fromNullable( ciphertext );
    }

    public Map<UUID, ObjectMetadataEncryptedNode> getChildren() {
        return children;
    }

    public void addChild( UUID childId, ObjectMetadataEncryptedNode childNode ) {
        children.put( childId, childNode );
    }

    public void addChildren( Map<UUID, ObjectMetadataEncryptedNode> kids ) {
        children.putAll( kids );
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public Optional<BlockCiphertext> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ObjectMetadataNode [children=" + children + ", metadata=" + metadata + ", data=" + data + "]";
    }

    public static String printMap( Map<UUID, ObjectMetadataEncryptedNode> map ) {
        StringBuilder build = new StringBuilder();
        if ( map.isEmpty() ) {
            return "\"empty\",\n";
        }
        build.append( "{\n" );
        for ( Entry<UUID, ObjectMetadataEncryptedNode> ent : map.entrySet() ) {
            ObjectMetadataEncryptedNode value = ent.getValue();
            build.append( ent.getKey() );
            build.append( ": " );
            if ( value.getChildren() != null ) {
                build.append( printMap( value.getChildren() ) );
            }
        }
        build.append( "}\n" );
        return build.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( children == null ) ? 0 : children.hashCode() );
        result = prime * result + ( ( data == null ) ? 0 : data.hashCode() );
        result = prime * result + ( ( metadata == null ) ? 0 : metadata.hashCode() );
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
        if ( !( obj instanceof ObjectMetadataEncryptedNode ) ) {
            return false;
        }
        ObjectMetadataEncryptedNode other = (ObjectMetadataEncryptedNode) obj;
        if ( children == null ) {
            if ( other.children != null ) {
                return false;
            }
        } else if ( !children.equals( other.children ) ) {
            return false;
        }
        if ( data == null ) {
            if ( other.data != null ) {
                return false;
            }
        } else if ( !data.equals( other.data ) ) {
            return false;
        }
        if ( metadata == null ) {
            if ( other.metadata != null ) {
                return false;
            }
        } else if ( !metadata.equals( other.metadata ) ) {
            return false;
        }
        return true;
    }

}
