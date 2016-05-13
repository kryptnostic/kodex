package com.kryptnostic.v2.storage.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.v2.constants.Names;

public class Event {
    private final UUID   eventType;
    private final UUID   source;
    private final UUID   sourceType;
    private final String contents;

    @JsonCreator
    public Event(
            @JsonProperty( Names.EVENT_TYPE_FIELD ) UUID eventType,
            @JsonProperty( Names.SOURCE_TYPE_FIELD ) UUID sourceType,
            @JsonProperty( Names.SOURCE ) UUID source,
            @JsonProperty( Names.CONTENTS ) String contents) {
        this.eventType = eventType;
        this.sourceType = sourceType;
        this.source = source;
        this.contents = contents;
    }

    @JsonProperty( Names.EVENT_TYPE_FIELD )
    public UUID getEventType() {
        return eventType;
    }

    @JsonProperty( Names.SOURCE_TYPE_FIELD )
    public UUID getSource() {
        return source;
    }

    @JsonProperty( Names.SOURCE )
    public UUID getSourceType() {
        return sourceType;
    }

    @JsonProperty( Names.CONTENTS )
    public String getContents() {
        return contents;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static final class EventBuilder {
        private UUID   eventType;
        private UUID   source;
        private UUID   sourceType;
        private String contents;

        public EventBuilder() {}

        public EventBuilder eventType( UUID eventType ) {
            this.eventType = Preconditions.checkNotNull( eventType, "Event type UUID cannot be null" );
            return this;
        }

        public EventBuilder source( UUID source ) {
            this.source = Preconditions.checkNotNull( source, "Source UUID cannot be null." );
            return this;
        }

        public EventBuilder sourceType( UUID sourceType ) {
            this.sourceType = Preconditions.checkNotNull( sourceType, "Source type UUID cannot be null." );
            return this;
        }

        public EventBuilder data( String contents ) {
            this.contents = Preconditions.checkNotNull( contents, "Event contents cannot be null" );
            return this;
        }

        public Event build() {
            return new Event( eventType, sourceType, source, contents );
        }
    }
}
