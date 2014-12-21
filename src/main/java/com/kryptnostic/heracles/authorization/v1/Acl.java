package com.kryptnostic.heracles.authorization.v1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.kryptnostic.kodex.v1.constants.Names;

public class Acl {
    private static final String ACE = "ace";
    private final int       id;
    private final List<Ace> aces;

    @JsonCreator
    public Acl(
            @JsonProperty(Names.ID_FIELD) int id, 
            @JsonProperty(ACE) List<Ace> aces) {
        Preconditions.checkNotNull( aces );
        Preconditions.checkArgument( aces.size() > 0 , "At least one Ace must be provided." );
        this.id = id;
        this.aces = ImmutableList.copyOf( aces );
    }

    public Acl(int id, Ace... aces) {
        Preconditions.checkNotNull( aces , "Aces cannot be null." );
        Preconditions.checkArgument( aces.length > 0 , "At least one Ace must be provided." );
        this.id = id;
        this.aces = ImmutableList.copyOf( aces );
    }

    @JsonProperty(Names.ID_FIELD)
    public int getId() {
        return id;
    }

    @JsonProperty(ACE)
    public List<Ace> getAces() {
        return aces;
    }
}
