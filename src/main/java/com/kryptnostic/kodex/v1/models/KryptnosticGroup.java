package com.kryptnostic.kodex.v1.models;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class KryptnosticGroup implements Group {
    private final UUID      id;
    private final String    name;
    private final Set<UUID> groups;
    private final Set<UUID> users;

    public KryptnosticGroup(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.NAME_FIELD ) String name,
            @JsonProperty( Names.GROUP_PROPERTY ) Set<UUID> groups,
            @JsonProperty( Names.USERS_FIELD ) Set<UUID> users ) {
        this.id = id;
        this.name = name;
        this.groups = groups;
        this.users = users;
    }

    @Override
    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @Override
    @JsonProperty( Names.NAME_FIELD )
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty( Names.GROUP_PROPERTY )
    public Set<UUID> getGroups() {
        return groups;
    }

    @Override
    @JsonProperty( Names.USERS_FIELD )
    public Set<UUID> getUsers() {
        return users;
    }

}
