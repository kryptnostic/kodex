package com.kryptnostic.directory.v1.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.domain.DomainSharingPolicy;
import com.kryptnostic.kodex.v1.constants.Names;

public class DomainUpdate {
    private final UUID                          domainId;
    private final Optional<DomainSharingPolicy> sharingPolicy;
    private final Optional<Boolean>             publiclyListable;
    private final Boolean                       openRegistrationEnabled;

    @JsonCreator
    public DomainUpdate(
            @JsonProperty( Names.DOMAIN_FIELD ) UUID domainId,
            @JsonProperty( Names.SHARING_POLICY ) Optional<DomainSharingPolicy> sharingPolicy,
            @JsonProperty( Names.OPEN_REGISTRATION_ALLOWED ) Optional<Boolean> openRegistrationEnabled,
            @JsonProperty( Names.PUBLIC ) Optional<Boolean> publiclyListable ) {
        this.domainId = domainId;
        this.sharingPolicy = sharingPolicy;
        this.openRegistrationEnabled = openRegistrationEnabled.or( true );
        this.publiclyListable = publiclyListable;
    }

    @JsonProperty( Names.DOMAIN_FIELD )
    public UUID getDomainId() {
        return domainId;
    }

    @JsonProperty( Names.SHARING_POLICY )
    public Optional<DomainSharingPolicy> getSharingPolicy() {
        return sharingPolicy;
    }

    @JsonProperty( Names.PUBLIC )
    public Optional<Boolean> getPubliclyListable() {
        return publiclyListable;
    }

    @JsonProperty( Names.OPEN_REGISTRATION_ALLOWED )
    public Boolean getOpenRegistrationEnabled() {
        return openRegistrationEnabled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( domainId == null ) ? 0 : domainId.hashCode() );
        result = prime * result + ( ( publiclyListable == null ) ? 0 : publiclyListable.hashCode() );
        result = prime * result + ( ( sharingPolicy == null ) ? 0 : sharingPolicy.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        DomainUpdate other = (DomainUpdate) obj;
        if ( domainId == null ) {
            if ( other.domainId != null ) return false;
        } else if ( !domainId.equals( other.domainId ) ) return false;
        if ( publiclyListable == null ) {
            if ( other.publiclyListable != null ) return false;
        } else if ( !publiclyListable.equals( other.publiclyListable ) ) return false;
        if ( sharingPolicy == null ) {
            if ( other.sharingPolicy != null ) return false;
        } else if ( !sharingPolicy.equals( other.sharingPolicy ) ) return false;
        if ( openRegistrationEnabled == null ) {
            if ( other.openRegistrationEnabled != null ) return false;
        } else if ( !openRegistrationEnabled.equals( other.openRegistrationEnabled ) ) return false;
        return true;
    }

}
