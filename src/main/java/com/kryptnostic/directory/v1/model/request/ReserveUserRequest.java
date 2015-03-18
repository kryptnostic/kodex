package com.kryptnostic.directory.v1.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Request to reserve a user account.
 * 
 * @author Nick Hewitt
 *
 */
public class ReserveUserRequest {
    private final String realm;
    private final String username;
    private final String email;
    private final String givenName;

    @JsonCreator
    public ReserveUserRequest(
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.NAME_FIELD ) String username,
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.GIVEN_NAME_FIELD ) String givenName ) {
        this.realm = realm;
        this.username = username;
        this.email = email;
        this.givenName = givenName;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getRealm() {
        return realm;
    }

    @JsonProperty( Names.NAME_FIELD )
    public String getUsername() {
        return username;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.GIVEN_NAME_FIELD )
    public String getGivenName() {
        return givenName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
        result = prime * result + ( ( givenName == null ) ? 0 : givenName.hashCode() );
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
        result = prime * result + ( ( username == null ) ? 0 : username.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        ReserveUserRequest other = (ReserveUserRequest) obj;
        if ( email == null ) {
            if ( other.email != null ) return false;
        } else if ( !email.equals( other.email ) ) return false;
        if ( givenName == null ) {
            if ( other.givenName != null ) return false;
        } else if ( !givenName.equals( other.givenName ) ) return false;
        if ( realm == null ) {
            if ( other.realm != null ) return false;
        } else if ( !realm.equals( other.realm ) ) return false;
        if ( username == null ) {
            if ( other.username != null ) return false;
        } else if ( !username.equals( other.username ) ) return false;
        return true;
    }
}
