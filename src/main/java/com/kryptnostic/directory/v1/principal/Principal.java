package com.kryptnostic.directory.v1.principal;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public interface Principal {
    @JsonProperty( Names.ID_FIELD )
    UUID getId();
}
