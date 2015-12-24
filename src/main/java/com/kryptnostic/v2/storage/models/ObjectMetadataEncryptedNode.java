package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.constants.Names;
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
    private final Optional<BlockCiphertext>              ciphertext;
    private final Optional<BlockCiphertext>              encryptedCryptoService;

    public ObjectMetadataEncryptedNode( ObjectMetadata metadata ) {
        this(
                metadata,
                Optional.<BlockCiphertext> absent(),
                Optional.<BlockCiphertext> absent(),
                Maps.<UUID, ObjectMetadataEncryptedNode> newHashMap() );
    }

    @JsonCreator
    public ObjectMetadataEncryptedNode(
            @JsonProperty( Names.METADATA_FIELD ) ObjectMetadata metadata,
            @JsonProperty( Names.CONTENTS ) Optional<BlockCiphertext> ciphertext,
            @JsonProperty( Names.KEY_FIELD ) Optional<BlockCiphertext> encryptedCryptoService,
            @JsonProperty( Names.CHILDREN_FIELD ) Map<UUID, ObjectMetadataEncryptedNode> children) {
        this.metadata = metadata;
        this.ciphertext = ciphertext;
        this.encryptedCryptoService = encryptedCryptoService;
        this.children = children;
    }

    public ObjectMetadataEncryptedNode( ObjectMetadata metadata, Optional<BlockCiphertext> ciphertext ) {
        this(
                metadata,
                ciphertext,
                Optional.<BlockCiphertext> absent(),
                Maps.<UUID, ObjectMetadataEncryptedNode> newHashMap() );
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
        return ciphertext;
    }

    public Optional<BlockCiphertext> getEncryptedCryptoService() {
        return encryptedCryptoService;
    }

    @Override
    public String toString() {
        return "ObjectMetadataNode [children=" + children + ", metadata=" + metadata + ", ciphertext=" + ciphertext
                + "]";
    }

    public static String printMap( Map<UUID, ObjectMetadataEncryptedNode> map ) {
        return "\n" + printMap( map, "\t" );
    }

    public static String printMap( Map<UUID, ObjectMetadataEncryptedNode> map, String prefix ) {
        StringBuilder build = new StringBuilder();
        if ( map.isEmpty() ) {
            return "[/]\n";
        }
        build.append( "{\n" );
        for ( Entry<UUID, ObjectMetadataEncryptedNode> ent : map.entrySet() ) {
            ObjectMetadataEncryptedNode value = ent.getValue();
            build.append( prefix );
            build.append( ent.getKey() );
            build.append( ": " );
            if ( value.getChildren() != null ) {
                build.append( printMap( value.getChildren(), prefix + "\t" ) );
            }
        }
        build.append( prefix + "}\n" );
        return build.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( children == null ) ? 0 : children.hashCode() );
        result = prime * result + ( ( ciphertext == null ) ? 0 : ciphertext.hashCode() );
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
        if ( ciphertext == null ) {
            if ( other.ciphertext != null ) {
                return false;
            }
        } else if ( !ciphertext.equals( other.ciphertext ) ) {
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
