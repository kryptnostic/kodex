package com.kryptnostic.heracles.authorization.v1;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Class for requesting creation of an ACE from the server. If the ACE already exists, it will return the id
 * for the existing ACE, instead of creating a new one.
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public class AceCreationRequest {
    private final Set<String> groups;
    private final Set<String> users;
    private final Permissions permissions;
    
    @JsonCreator
    public AceCreationRequest( 
            @JsonProperty( Names.USERS_FIELD ) Set<String> users, 
            @JsonProperty( Names.GROUPS_PROPERTY ) Set<String> groups , 
            @JsonProperty( Names.PERMISSIONS_PROPERTY ) Permissions permissions ) {
        this.users = users;
        this.groups = groups;
        this.permissions = permissions;
    }
    
    @JsonProperty( Names.USERS_FIELD )
    public Set<String> getUsers() {
        return users;
    }
    
    @JsonProperty( Names.GROUPS_PROPERTY )
    public Set<String> getGroups() {
        return groups;
    }
    
    @JsonProperty( Names.PERMISSIONS_PROPERTY )
    public Permissions getPermissions() {
        return permissions;
    }

}
