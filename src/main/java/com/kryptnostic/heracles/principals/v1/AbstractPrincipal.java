package com.kryptnostic.heracles.principals.v1;

import static com.kryptnostic.kodex.v1.constants.Names.NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.models.Principal;

public class AbstractPrincipal implements Principal {
    protected final String    name;
    protected final String    realm;

    public AbstractPrincipal(String realm, String name) {
        this.name = name;
        this.realm = realm;
    }

    @Override
    @JsonProperty(NAME_FIELD)
    public String getName() {
        return name;
    }
    
    @Override
    @JsonProperty(REALM_FIELD) 
    public String getRealm() {
        return realm;
    }
}
