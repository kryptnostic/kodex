package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {

    private static final String BODY = "body";

    private final String body;

    @JsonCreator
    public Document(@JsonProperty(BODY) String body) {
        this.body = body;
    }

    @JsonProperty(BODY)
    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        Document d = (Document) o;
        return d.body.equals(body);
    }
}