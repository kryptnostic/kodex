package com.kryptnostic.directory.v1.domain;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class Domain {
    private final UUID    id;
    private final String  name;
    private final AtomicInteger size; //Total number of users in domain
    private final boolean confirmationEmailRequired;

    @JsonCreator
    public Domain(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.REALM_FIELD ) String name,
            @JsonProperty( Names.SIZE_FIELD ) int size, 
            @JsonProperty( Names.CONFIRMATION_FIELD ) Optional<Boolean> confirmationEmailRequired ) {
        this.id = id;
        this.name = name;
        this.size = new AtomicInteger( size );
        this.confirmationEmailRequired = confirmationEmailRequired.or( false );
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getName() {
        return name;
    }
    
    @JsonProperty( Names.SIZE_FIELD )
    public int getSize() {
        return size.get();
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailRequired() {
        return confirmationEmailRequired;
    }
    
    @JsonIgnore
    public Domain incrementSize() {
        size.incrementAndGet();
        return this;
    }

    @Override
    public String toString() {
        return "Domain [id=" + id + ", name=" + name + ", size=" + size + ", confirmationEmailRequired="
                + confirmationEmailRequired + "]";
    }

}
