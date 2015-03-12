package com.kryptnostic.directory.v1.principal;

import static com.kryptnostic.kodex.v1.constants.Names.NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.directory.v1.model.AbstractKey;

public class UserKey extends AbstractKey implements Serializable {
    private static final long serialVersionUID = 1392846078227265603L;

    // TODO: Make Realm its own class to avoid confusion when calling API.
    @JsonCreator
    public UserKey( @JsonProperty( REALM_FIELD ) String realm, @JsonProperty( NAME_FIELD ) String name ) {
        super( realm, normalizeName( name ) );
    }

    public static UserKey fromString( final String fqn ) {
        List<String> parts = splitter.splitToList( fqn );
        Preconditions.checkState( parts.size() == 2, "FQN should only yield two parts." );
        String realm = parts.get( 0 );
        String name = parts.get( 1 );
        Preconditions.checkState( isValidRealm( realm ), "Invalid realm" );
        Preconditions.checkState( isValidUsername( name ), "Invalid name" );
        return new UserKey( realm, name );
    }

    public static String buildFqn( String realm, String name ) {
        String normed = normalizeName( name );
        return realm + DELIMITER + normed;
    }

    /**
     * Usernames are case-insensitive.
     * 
     * @param name
     * @return
     */
    public static String normalizeName( String name ) {
        return StringUtils.lowerCase( name, Locale.ROOT );
    }

    @Override
    public String toString() {
        return buildFqn( realm, name );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof UserKey ) ) {
            return false;
        }
        UserKey other = (UserKey) obj;
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        } else if ( !name.equals( other.name ) ) {
            return false;
        }
        if ( realm == null ) {
            if ( other.realm != null ) {
                return false;
            }
        } else if ( !realm.equals( other.realm ) ) {
            return false;
        }
        return true;
    }

}
