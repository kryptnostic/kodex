package com.kryptnostic.storage.v1.models.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.DocumentBlock;

public class DocumentResponse extends BasicResponse<Document> {

    @JsonCreator
    public DocumentResponse(@JsonProperty(DATA) Document document, @JsonProperty(STATUS) int status,
            @JsonProperty(SUCCESS) boolean success) {
        super(document, status, success);
    }

    @JsonIgnore
    public DocumentResponse(String documentId, Collection<DocumentBlock> blocks, String verify, int status, boolean success) {
        super(new Document(documentId, blocks, verify), status, success);
    }

}
