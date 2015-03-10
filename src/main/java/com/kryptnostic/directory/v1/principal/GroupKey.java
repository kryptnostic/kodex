package com.kryptnostic.directory.v1.principal;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.directory.v1.model.AbstractKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class GroupKey extends AbstractKey implements Serializable {
    private static final long serialVersionUID = 1392846078227265603L;

    @JsonCreator
    public GroupKey( @JsonProperty( Names.REALM_FIELD ) String realm, @JsonProperty( Names.NAME_FIELD ) String name ) {
        super( realm, name );
    }

    public static String buildFqn( String realm, String name ) {
        return realm + DELIMITER + name;
    }

    public static GroupKey fromString( String fqn ) {
        List<String> parts = splitter.splitToList( fqn );
        Preconditions.checkState( parts.size() == 2, "FQN should only yield two parts." );
        String realm = parts.get( 0 );
        String name = parts.get( 1 );
        isValidRealm( realm );
        isValidUsername( name );
        return new GroupKey( realm, name );
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
        if ( !( obj instanceof GroupKey ) ) {
            return false;
        }
        GroupKey other = (GroupKey) obj;
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
