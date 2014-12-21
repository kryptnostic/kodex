package com.kryptnostic.heracles.principals.v1.requests;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

public class GroupCreationRequest {
    private static final String NAME_PROPERTY   = "name";
    private static final String GROUPS_PROPERTY = "groups";

    private String              name;
    private final Set<String> groups;

    public GroupCreationRequest(String name) {
        this( name , Sets.<String>newHashSet() );
    }

    public GroupCreationRequest(String name, Set<String> groups) {
        this.name = name;
        this.groups = groups;
    }

    public GroupCreationRequest addGroup(String group) {
        this.groups.add( group );
        return this;
    }

    @JsonProperty(NAME_PROPERTY)
    public String getName() {
        return name;
    }

    @JsonProperty(GROUPS_PROPERTY)
    public Set<String> getGroups() {
        return groups;
    }
}
