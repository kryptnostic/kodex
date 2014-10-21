package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentMetadata implements Serializable {
    public static final String FIELD_ID = "id";
    public static final String FIELD_VERIFY = "verify";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_NUM_BLOCKS = "numBlocks";

    private final String id;
    private final String verify;
    private final int version;
    private final int numBlocks;

    @JsonIgnore
    public DocumentMetadata(String id, String verify, int numBlocks) {
        this.id = id;
        this.verify = verify;
        this.version = 0;
        this.numBlocks = numBlocks;
    }

    @JsonCreator
    public DocumentMetadata(@JsonProperty(FIELD_ID) String id, @JsonProperty(FIELD_VERIFY) String verify,
            @JsonProperty(FIELD_VERSION) int version, @JsonProperty(FIELD_NUM_BLOCKS) int numBlocks) {
        this.id = id;
        this.verify = verify;
        this.version = version;
        this.numBlocks = numBlocks;
    }

    @JsonProperty(FIELD_ID)
    public String getId() {
        return id;
    }

    @JsonProperty(FIELD_VERSION)
    public int getVersion() {
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

    @JsonProperty(FIELD_NUM_BLOCKS)
    public int getNumBlocks() {
        return numBlocks;
    }

    @Override
    public boolean equals(Object obj) {
        DocumentMetadata other = (DocumentMetadata) obj;
        return id.equals(other.id) && verify.equals(other.verify) && version == other.version;
    }
}
