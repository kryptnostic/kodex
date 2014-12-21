package com.kryptnostic.heracles.authorization.v1;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class Ace {
    private final int id;
    private final Set<String> groups;
    private final Set<String> users;
    private final Permissions permissions;
    
    @JsonCreator
    public Ace( 
            @JsonProperty( Names.ID_FIELD ) int id , 
            @JsonProperty( Names.USERS_FIELD ) Set<String> users, 
            @JsonProperty( Names.GROUPS_PROPERTY ) Set<String> groups , 
            @JsonProperty( Names.PERMISSIONS_PROPERTY ) Permissions permissions ) {
        this.id = id; 
        this.users = users;
        this.groups = groups;
        this.permissions = permissions;
    }
    
    @JsonProperty( Names.ID_FIELD )
    public int getId() {
        return id;
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
