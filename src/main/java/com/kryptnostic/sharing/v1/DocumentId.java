package com.kryptnostic.sharing.v1;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.users.v1.UserKey;

public class DocumentId implements Serializable {
    private static final long         serialVersionUID = 8301034434310597675L;

    private static final ObjectMapper mapper           = KodexObjectMapperFactory.getObjectMapper();
    private static final Base64       codec            = new Base64();

    protected final String            documentId;
    protected final UserKey           user;

    @JsonCreator
    public DocumentId( @JsonProperty( Names.ID_FIELD ) String documentId, @JsonProperty( Names.USER_FIELD ) UserKey user ) {
        this.documentId = documentId;
        this.user = user;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getDocumentId() {
        return documentId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UserKey getUser() {
        return user;
    }

    public static DocumentId fromUserAndId( String id, UserKey user ) {
        return new DocumentId( id, user );
    }

    public static DocumentId fromString( String base64Hash ) throws JsonParseException, JsonMappingException,
            IOException {
        byte[] bytes = codec.decode( base64Hash );
        return mapper.readValue( bytes, DocumentId.class );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( documentId == null ) ? 0 : documentId.hashCode() );
        result = prime * result + ( ( user == null ) ? 0 : user.hashCode() );
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
        if ( !( obj instanceof DocumentId ) ) {
            return false;
        }
        DocumentId other = (DocumentId) obj;
        if ( documentId == null ) {
            if ( other.documentId != null ) {
                return false;
            }
        } else if ( !documentId.equals( other.documentId ) ) {
            return false;
        }
        if ( user == null ) {
            if ( other.user != null ) {
                return false;
            }
        } else if ( !user.equals( other.user ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        try {
            return codec.encodeAsString( mapper.writeValueAsBytes( this ) );
        } catch ( JsonProcessingException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return super.toString();
        }
    }
}
