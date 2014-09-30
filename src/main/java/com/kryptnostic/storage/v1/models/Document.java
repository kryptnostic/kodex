package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.Encryptable;

public class Document {

    private static final String BODY = "body";

    private final Encryptable<String> body;

    @JsonCreator
    public Document(@JsonProperty(BODY) Encryptable<String> body) {
        this.body = body;
    }

    @JsonProperty(BODY)
    public Encryptable<String> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        Document d = (Document) o;
        return d.body.equals(body);
    }
}
