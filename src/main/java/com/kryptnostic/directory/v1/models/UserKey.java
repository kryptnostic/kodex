package com.kryptnostic.directory.v1.models;

import static com.kryptnostic.kodex.v1.constants.Names.NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

public class UserKey implements Principal, Serializable {
    private static final long       serialVersionUID    = 1392846078227265603L;
    protected static final Splitter splitter            = Splitter.on( "." ).trimResults().omitEmptyStrings();
    protected final String          name;
    protected final String          realm;
    public static final int         MAX_USERNAME_LENGTH = 1024;
    public static final int         MAX_REALM_LENGTH    = 1024;

    // TODO: Make Realm its own class to avoid confusion when calling API.
    @JsonCreator
    public UserKey( @JsonProperty( REALM_FIELD ) String realm, @JsonProperty( NAME_FIELD ) String name ) {
        this.name = normalizeUsername( name );
        this.realm = realm;
    }

    // Username is case-insensitive
    private static String normalizeUsername( String username ) {
        return StringUtils.lowerCase( username, Locale.ROOT );
    }

    public String getName() {
        return name;
    }

    public String getRealm() {
        return realm;
    }

    @Override
    public String toString() {
        return buildFqn( realm, name );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
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

    public static boolean isValidUsername( String name ) {
        Preconditions.checkState(
                StringUtils.isAlphanumeric( name ),
                "Realm name cannot be blank and must consist of only alpha numeric characters." );
        Preconditions.checkState( name.length() < MAX_USERNAME_LENGTH, "Username cannot be longer than "
                + MAX_USERNAME_LENGTH + "characters" );
        return true;
    }

    public static boolean isValidRealm( String realm ) {
        Preconditions.checkState(
                StringUtils.isAlphanumeric( realm ),
                "Realm name cannot be blank and must consist of only alpha numeric characters." );
        Preconditions.checkState( realm.length() < MAX_REALM_LENGTH, "Realm name cannot be longer than "
                + MAX_REALM_LENGTH + "characters" );
        return false;
    }

    public static String buildFqn( String realm, String name ) {
        return realm + "." + normalizeUsername( name );
    }

    public static UserKey fromString( final String fqn ) {
        List<String> parts = splitter.splitToList( fqn );
        Preconditions.checkState( parts.size() == 2, "FQN should only yield two parts." );
        String realm = parts.get( 0 );
        String name = parts.get( 1 );
        isValidRealm( realm );
        isValidUsername( name );
        return new UserKey( realm, name );
    }

}
