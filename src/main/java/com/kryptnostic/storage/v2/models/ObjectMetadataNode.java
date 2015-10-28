package com.kryptnostic.storage.v2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

/**
 * This class is a return only JSON data type that used to return nest object tree with objects at various load levels
 * from the server.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class ObjectMetadataNode {
    private final Map<UUID, ObjectMetadataNode> children;
    private final ObjectMetadata                metadata;
    private final Optional<BlockCiphertext>     data;

    public ObjectMetadataNode( ObjectMetadata metadata ) {
        this( metadata, null );
    }

    public ObjectMetadataNode( ObjectMetadata metadata, BlockCiphertext ciphertext ) {
        this.children = new HashMap<>();
        this.metadata = metadata;
        this.data = Optional.fromNullable( ciphertext );
    }

    public Map<UUID, ObjectMetadataNode> getChildren() {
        return children;
    }

    public void addChild( UUID childId, ObjectMetadataNode childNode ) {
        children.put( childId, childNode );
    }

    public void addChildren( Map<UUID, ObjectMetadataNode> kids ) {
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

    public static String printMap( Map<UUID, ObjectMetadataNode> map ) {
        StringBuilder build = new StringBuilder();
        if ( map.isEmpty() ) {
            return "\"empty\",\n";
        }
        build.append( "{\n" );
        for ( Entry<UUID, ObjectMetadataNode> ent : map.entrySet() ) {
            ObjectMetadataNode value = ent.getValue();
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
        if ( !( obj instanceof ObjectMetadataNode ) ) {
            return false;
        }
        ObjectMetadataNode other = (ObjectMetadataNode) obj;
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
