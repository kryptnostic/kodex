package com.kryptnostic.storage.v1.models.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentFragmentRequest {
    private static final String FIELD_OFFSETS = "offsets";
    private static final String FIELD_WINDOW = "window";

    private final List<Integer> offsets;
    private final int characterWindow;

    @JsonCreator
    public DocumentFragmentRequest(@JsonProperty(FIELD_OFFSETS) List<Integer> offsets,
            @JsonProperty(FIELD_WINDOW) int characterWindow) {
        this.offsets = offsets;
        this.characterWindow = characterWindow;
    }

    @JsonProperty(FIELD_OFFSETS)
    public List<Integer> getOffsets() {
        return offsets;
    }

    @JsonProperty(FIELD_WINDOW)
    public int getCharacterWindow() {
        return characterWindow;
    }
}
