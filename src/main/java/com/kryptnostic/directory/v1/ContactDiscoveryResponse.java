package com.kryptnostic.directory.v1;

import java.util.SortedMap;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSortedMap;
import com.kryptnostic.directory.v1.principal.User;

public class ContactDiscoveryResponse {
    public static final String                              EMAIL_RESULTS_FIELD    = "emailResults";
    public static final String                              USERNAME_RESULTS_FIELD = "usernameResults";
    public static final String                              NAME_RESULTS_FIELD     = "nameResults";
    private final SortedMap<Integer, SortedMap<UUID, User>> emailResults;
    private final SortedMap<Integer, SortedMap<UUID, User>> usernameResults;
    private final SortedMap<Integer, SortedMap<UUID, User>> nameResults;

    @JsonCreator
    public ContactDiscoveryResponse(
            @JsonProperty( EMAIL_RESULTS_FIELD ) SortedMap<Integer, SortedMap<UUID, User>> emailResults,
            @JsonProperty( USERNAME_RESULTS_FIELD ) SortedMap<Integer, SortedMap<UUID, User>> usernameResults,
            @JsonProperty( NAME_RESULTS_FIELD ) SortedMap<Integer, SortedMap<UUID, User>> nameResults ) {
        this.emailResults = emailResults;
        this.usernameResults = usernameResults;
        this.nameResults = nameResults;
    }

    @JsonProperty( EMAIL_RESULTS_FIELD )
    public SortedMap<Integer, SortedMap<UUID, User>> getEmailResults() {
        return emailResults;
    }

    @JsonProperty( USERNAME_RESULTS_FIELD )
    public SortedMap<Integer, SortedMap<UUID, User>> getUsernameResults() {
        return usernameResults;
    }

    @JsonProperty( NAME_RESULTS_FIELD )
    public SortedMap<Integer, SortedMap<UUID, User>> getNameResults() {
        return nameResults;
    }

    public static ContactDiscoveryResponse noResults() {
        return new ContactDiscoveryResponse(
                ImmutableSortedMap.<Integer, SortedMap<UUID, User>> of(),
                ImmutableSortedMap.<Integer, SortedMap<UUID, User>> of(),
                ImmutableSortedMap.<Integer, SortedMap<UUID, User>> of() );
    }

}
