package com.kryptnostic.sharing.v1.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationPreference {

    private static final String FIELD_GCM_IDS = "gcmIds";

    private final List<String> gcmIds;

    @JsonCreator
    public NotificationPreference( @JsonProperty( FIELD_GCM_IDS ) List<String> gcmIds ) {
        this.gcmIds = gcmIds;
    }

    @JsonProperty( FIELD_GCM_IDS )
    public List<String> getGcmIds() {
        return this.gcmIds;
    }
}
