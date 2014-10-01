package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.storage.v1.models.Document;

public class DocumentRequest {
    private static final String DOCUMENT="document";
    private final Document document;

    @JsonCreator
    public DocumentRequest(@JsonProperty(DOCUMENT) Document document) {
        this.document = document;
    }
    
    @JsonProperty(DOCUMENT)
    public Document getDocument() {
        return document;
    }
}
