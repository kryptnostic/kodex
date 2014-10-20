package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentMetadata implements Serializable {
    public static final String FIELD_ID = "id";
    public static final String FIELD_VERIFY = "verify";
    public static final String FIELD_VERSION = "version";

    private final String id;
    private final String verify;
    private final Integer version;

    @JsonIgnore
    public DocumentMetadata(String id, String verify) {
        this.id = id;
        this.verify = verify;
        this.version = 0;
    }

    @JsonCreator
    public DocumentMetadata(@JsonProperty(FIELD_ID) String id, @JsonProperty(FIELD_VERIFY) String verify,
            @JsonProperty(FIELD_VERSION) Integer version) {
        this.id = id;
        this.verify = verify;
        this.version = version;
    }

    @JsonProperty(FIELD_ID)
    public String getId() {
        return id;
    }

    @JsonProperty(FIELD_VERSION)
    public Integer getVersion() {
        return version;
    }

    /**
     * So verify
     * 
     * @return Verification hash of all blocks
     */
    @JsonProperty(FIELD_VERIFY)
    public String getVerify() {
        return verify;
    }

    @Override
    public boolean equals(Object obj) {
        DocumentMetadata other = (DocumentMetadata) obj;
        return id.equals(other.id) && verify.equals(other.verify) && version == other.version;
    }
}
