package com.kryptnostic.directory.v1.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class Domain {
    private final UUID   id;
    private final String name;

    @JsonCreator
    public Domain( @JsonProperty( Names.ID_FIELD ) UUID id, @JsonProperty( Names.REALM_FIELD ) String name ) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getName() {
        return name;
    }

}
