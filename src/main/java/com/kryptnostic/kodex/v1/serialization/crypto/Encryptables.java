package com.kryptnostic.kodex.v1.serialization.crypto;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Convenience class that allows for Encryptable objects to be representated as a collection without nested generic
 * types
 * 
 * This class is required for proper Hyperdex serialization of collections of Encryptable objects
 * 
 * @author sinaiman
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = As.PROPERTY,
    property = Names.CLASS_FIELD )
public class Encryptables extends ArrayList<Encryptable<?>> {
    private static final long serialVersionUID = -5684518728232242220L;

    /**
     * Creates an empty ArrayList of Encryptable objects
     */
    public Encryptables() {
        super();
    }

    /**
     * @param c Collection of Encryptable objects to be added into this ArrayList
     */
    public Encryptables( Collection<? extends Encryptable<?>> c ) {
        super( c );
    }

}