package com.kryptnostic.kodex.v1.models;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = As.PROPERTY,
        property = Encryptable.FIELD_CLASS )
public class Encryptables extends ArrayList<Encryptable<?>> {
    private static final long serialVersionUID = -5684518728232242220L;

    public Encryptables() {
        super();
    }

    public Encryptables( Collection<? extends Encryptable<?>> c ) {
        super( c );
    }
}