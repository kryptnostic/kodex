package com.kryptnostic.directory.v1.domain;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class Domain {
    public static final String        PUBLICLY_LISTABLE_FIELD         = "publiclyListable";
    public static final String        DOMAIN_SHARING_POLICY_FIELD     = "domainSharingPolicy";

    public static final boolean       CONFIRMATION_EMAIL_NOT_REQUIRED = false;
    public static final boolean       CONFIRMATION_EMAIL_REQUIRED     = true;
    public static final boolean       NOT_PUBLICLY_LISTABLE           = true;
    public static final boolean       PUBLICLY_LISTABLE               = true;

    private final UUID                id;
    private final String              name;
    private final AtomicInteger       size;                                                // Total number of users in
                                                                                            // domain
    private final DomainSharingPolicy domainSharingPolicy;
    private final boolean             confirmationEmailRequired;
    private final boolean             publiclyListable;

    @JsonCreator
    public Domain(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.DOMAIN_FIELD ) String name,
            @JsonProperty( Names.SIZE_FIELD ) int size,
            @JsonProperty( DOMAIN_SHARING_POLICY_FIELD ) DomainSharingPolicy domainSharingPolicy,
            @JsonProperty( Names.CONFIRMATION_FIELD ) Optional<Boolean> confirmationEmailRequired,
            @JsonProperty( PUBLICLY_LISTABLE_FIELD ) Optional<Boolean> publiclyListable ) {
        this.id = id;
        this.name = name;
        this.size = new AtomicInteger( size );
        this.domainSharingPolicy = domainSharingPolicy;
        this.confirmationEmailRequired = confirmationEmailRequired.or( CONFIRMATION_EMAIL_NOT_REQUIRED );
        this.publiclyListable = publiclyListable.or( PUBLICLY_LISTABLE );
    }

    public Domain( UUID id, String name, int size ) {
        this( id, name, size, DomainSharingPolicy.AllDomains, Optional.of( CONFIRMATION_EMAIL_NOT_REQUIRED ), Optional
                .of( PUBLICLY_LISTABLE ) );
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @JsonProperty( Names.DOMAIN_FIELD )
    public String getName() {
        return name;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public int getSize() {
        return size.get();
    }

    @JsonProperty( DOMAIN_SHARING_POLICY_FIELD )
    public DomainSharingPolicy getDomainSharingPolicy() {
        return domainSharingPolicy;
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailRequired() {
        return confirmationEmailRequired;
    }

    @JsonProperty( PUBLICLY_LISTABLE_FIELD )
    public boolean isPubliclyListable() {
        return publiclyListable;
    }

    @JsonIgnore
    public Domain incrementSize() {
        size.incrementAndGet();
        return this;
    }

    @Override
    public String toString() {
        return "Domain [id=" + id + ", name=" + name + ", size=" + size + ", domainSharingPolicy="
                + domainSharingPolicy.toString() + ", confirmationEmailRequired=" + confirmationEmailRequired + "]";
    }

}
