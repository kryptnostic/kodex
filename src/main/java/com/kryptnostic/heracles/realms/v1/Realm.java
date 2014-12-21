package com.kryptnostic.heracles.realms.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 * 
 * A type-safety wrapper around the realm name string.
 */
public class Realm {
    private static final String REALM_NAME_FIELD = "realmName";
    private final String name;
    
    @JsonCreator
    public Realm( @JsonProperty(REALM_NAME_FIELD) String name ) {
        this.name = name;
    }
    
    @JsonProperty(REALM_NAME_FIELD)
    public String getName() {
        return name;
    }
}
