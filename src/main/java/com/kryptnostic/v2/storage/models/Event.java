package com.kryptnostic.v2.storage.models;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.v2.constants.Names;

/**
 * Class representing plaintext events. The distinction between events and objects occurs primarily at the API level.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class Event {
    private final Set<PropagationDirections> propagationDirections;
    private final long                       ttlMillis;
    private final String                     contents;

    @JsonCreator
    public Event(
            @JsonProperty( Names.PROPAGATION_DIRECTION_FIED ) Set<PropagationDirections> propagationDirections,
            @JsonProperty( Names.TTL_MILLIS ) long ttlMillis,
            @JsonProperty( Names.CONTENTS ) String contents) {
        this.propagationDirections = propagationDirections;
        this.ttlMillis = ttlMillis;
        this.contents = contents;
    }

    @JsonProperty( Names.PROPAGATION_DIRECTION_FIED )
    public Set<PropagationDirections> getPropagationDirections() {
        return propagationDirections;
    }

    @JsonProperty( Names.CONTENTS )
    public String getContents() {
        return contents;
    }

    @JsonProperty( Names.TTL_MILLIS )
    public long getTtlMillis() {
        return ttlMillis;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static enum PropagationDirections {
        ASCENDING,
        DESCENDING
    }

    public static final class EventBuilder {
        private Set<PropagationDirections> propagationDirections = EnumSet.noneOf( PropagationDirections.class );
        private long                       ttlMillis;
        private String                     contents;

        public EventBuilder() {}

        public void ascendingPropgation() {
            propagationDirections.add( PropagationDirections.ASCENDING );
        }

        public void descendingPropgation() {
            propagationDirections.add( PropagationDirections.DESCENDING );
        }

        public void ttlMillis( long ttlMillis ) {
            this.ttlMillis = ttlMillis;
        }

        public void ttl( long duration, TimeUnit timeUnit ) {
            this.ttlMillis = timeUnit.toMillis( duration );
        }

        public EventBuilder data( String contents ) {
            this.contents = Preconditions.checkNotNull( contents, "Event contents cannot be null" );
            return this;
        }

        public Event build() {
            return new Event( propagationDirections, ttlMillis, contents );
        }
    }
}
