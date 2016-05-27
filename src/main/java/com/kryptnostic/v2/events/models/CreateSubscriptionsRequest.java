package com.kryptnostic.v2.events.models;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public class CreateSubscriptionsRequest {

    // Event Type -> ( Event [Type] -> Subscription ]
    private final Map<String, Set<Subscription>> eventTypesBySourceType;
    private final Map<String, Set<Subscription>> eventTypesBySource;

    public CreateSubscriptionsRequest(
            Map<String, Set<Subscription>> eventTypesBySourceType,
            Map<String, Set<Subscription>> eventTypesBySource ) {
        this.eventTypesBySource = eventTypesBySource;
        this.eventTypesBySourceType = eventTypesBySourceType;
    }

    public Map<String, Set<Subscription>> getEventTypesBySourceType() {
        return eventTypesBySourceType;
    }

    public Map<String, Set<Subscription>> getEventTypesBySource() {
        return eventTypesBySource;
    }

    public static final class SubscriptionRequestBuilder {
        ConcurrentMap<String, Set<Subscription>> eventTypesBySourceType = Maps.newConcurrentMap();
        ConcurrentMap<String, Set<Subscription>> eventTypesBySource     = Maps.newConcurrentMap();

        public void subscribeToEventTypeFromSource( String eventType, UUID source, Subscription subscription ) {
            Set<Subscription> subscriptionsForSource = eventTypesBySource.get( eventType );
            if ( subscriptionsForSource == null ) {
                subscriptionsForSource = Sets.newConcurrentHashSet();
                subscriptionsForSource = MoreObjects
                        .firstNonNull( eventTypesBySource.putIfAbsent( eventType,
                                subscriptionsForSource ), subscriptionsForSource );
            }
            subscriptionsForSource.add( subscription );
        }

        public void subscribeToEventTypeFromSourceType( String eventType, String source, Subscription subscription ) {
            Set<Subscription> subscriptionsForSourceType = eventTypesBySourceType.get( eventType );
            if ( subscriptionsForSourceType == null ) {
                subscriptionsForSourceType = Sets.newConcurrentHashSet();
                subscriptionsForSourceType = MoreObjects
                        .firstNonNull( eventTypesBySourceType.putIfAbsent( eventType,
                                subscriptionsForSourceType ), subscriptionsForSourceType );
            }
            subscriptionsForSourceType.add( subscription );
        }

        public CreateSubscriptionsRequest build() {
            return new CreateSubscriptionsRequest( eventTypesBySourceType, eventTypesBySource );
        }
    }

    public static final class Subscription {
        private final Optional<VersionedObjectKey> source;
        private final Optional<String>             sourceType;
        private final Map<SubscriptionType, Long>  frequencies;

        public Subscription(
                Optional<VersionedObjectKey> source,
                Optional<String> sourceType,
                ImmutableMap<SubscriptionType, Long> frequencies ) {
            Preconditions.checkState( sourceType.isPresent() != source.isPresent(),
                    "Both source type and source cannot be present." );
            this.source = source;
            this.sourceType = sourceType;

            this.frequencies = frequencies;
        }

        public Optional<VersionedObjectKey> getSource() {
            return source;
        }

        public Optional<String> getSourceType() {
            return sourceType;
        }

        public Map<SubscriptionType, Long> getFrequencies() {
            return frequencies;
        }

        public static final class SubscriptionBuilder {
            Optional<String>                             sourceType;
            Optional<VersionedObjectKey>                 source;
            ImmutableMap.Builder<SubscriptionType, Long> frequencies = ImmutableMap.builder();

            void ephemeral() {
                frequencies.put( SubscriptionType.EPHEMERAL, 0L );
            }

            public void forSourceType( String sourceType ) {
                Preconditions.checkArgument( StringUtils.isNotBlank( sourceType ),
                        "Source type cannot be blank or null." );
                Preconditions.checkState( !source.isPresent(), "Source object cannot be present." );
                this.sourceType = Optional.of( sourceType );
            }

            public void forSource( VersionedObjectKey source ) {
                Preconditions.checkNotNull( sourceType, "Source cannot be null." );
                Preconditions.checkState( !sourceType.isPresent(), "Source type cannot be present." );
                this.source = Optional.of( source );
            }

            void sms( long frequency, TimeUnit timeUnit ) {
                frequencies.put( SubscriptionType.SMS, timeUnit.toMillis( frequency ) );
            }

            void email( long frequency, TimeUnit timeUnit ) {
                frequencies.put( SubscriptionType.EMAIL, timeUnit.toMillis( frequency ) );
            }

            public Subscription build() {
                return new Subscription( source, sourceType, frequencies.build() );
            }
        }
    }

    public static final SubscriptionRequestBuilder builder() {
        return new SubscriptionRequestBuilder();
    }
}
