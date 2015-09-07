package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.EncryptedSearchObjectKey;

public class PairedEncryptedSearchObjectKey implements Serializable {
    private static final long              serialVersionUID = -6584084313085024163L;
    private final EncryptedSearchObjectKey searchObjectKey;
    private final String                   objectId;

    public PairedEncryptedSearchObjectKey(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD ) final EncryptedSearchObjectKey searchObjectKey ) {
        this.objectId = objectId;
        this.searchObjectKey = searchObjectKey;
    }

    @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD )
    public EncryptedSearchObjectKey getSearchObjectKey() {
        return searchObjectKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }
}
