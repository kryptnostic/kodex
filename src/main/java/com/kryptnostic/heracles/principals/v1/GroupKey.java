package com.kryptnostic.heracles.principals.v1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;


public class GroupKey extends UserKey {
    private static final long serialVersionUID = 1392846078227265603L;
    
    @JsonCreator
    public GroupKey(
            @JsonProperty(Names.REALM_FIELD) String realm, 
            @JsonProperty(Names.NAME_FIELD) String name) {
        super( realm , name );
    }
    
    public static GroupKey fromString( String fqn ) {
        List<String> parts = splitter.splitToList( fqn );
        Preconditions.checkState( parts.size() == 2 , "FQN should only yield two parts." );
        String realm = parts.get( 0 );
        String name = parts.get( 1 );
        UserKey.isValidRealm( realm );
        UserKey.isValidUsername( name );
        return new GroupKey( realm , name );
    }
}
