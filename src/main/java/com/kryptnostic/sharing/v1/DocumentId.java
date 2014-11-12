package com.kryptnostic.sharing.v1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.users.v1.UserKey;

public class DocumentId implements Serializable {
    private static final ObjectMapper               mapper             = KodexObjectMapperFactory.getObjectMapper();
    private static final ObjectMapper               smile              = KodexObjectMapperFactory.getSmileMapper();

    protected final String                          documentId;
    protected final UserKey                         user;

    private static Function<DocumentId, String>     stringifier        = new Function<DocumentId, String>() {
                                                                           @Override
                                                                           public String apply( DocumentId input ) {
                                                                               return input.getDocumentId();
                                                                           }
                                                                       };

    private static Function<DocumentId, DocumentId> documentIdFunction = new Function<DocumentId, DocumentId>() {
                                                                           @Override
                                                                           public DocumentId apply( DocumentId input ) {
                                                                               return input;
                                                                           }
                                                                       };

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

    public static DocumentId fromIdAndUser( String id, UserKey user ) {
        return new DocumentId( id, user );
    }

    public byte[] asBytes() throws JsonProcessingException {
        return smile.writeValueAsBytes( this );
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

    public static Function documentIdFunction() {
        return documentIdFunction;
    }

    
    @Override
    public String toString() {
    		return "DocumentId [user={" + user.getRealm() + "," + user.getName() + "},id=" + documentId +"]";
    }
}
