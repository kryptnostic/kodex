package com.kryptnostic.directory.v1.domain;

import static com.kryptnostic.directory.v1.domain.DomainSharingPolicyNames.ALL_DOMAINS;
import static com.kryptnostic.directory.v1.domain.DomainSharingPolicyNames.INTERNAL_ONLY;
import static com.kryptnostic.directory.v1.domain.DomainSharingPolicyNames.TRUSTED_DOMAINS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

;
public enum DomainSharingPolicy {
    AllDomains( ALL_DOMAINS ), TrustedDomains( TRUSTED_DOMAINS ), InternalOnly( INTERNAL_ONLY );

    private static final Logger logger = LoggerFactory.getLogger( DomainSharingPolicy.class );
    private final String domainSharingPolicy;

    private DomainSharingPolicy( String domainSharingPolicy ) {
        this.domainSharingPolicy = domainSharingPolicy;
    }

    @JsonCreator
    public static DomainSharingPolicy createDomainSharingPolicy( String domainSharingPolicy ) {
        switch ( domainSharingPolicy ) {
            case ALL_DOMAINS:
                return AllDomains;
            case TRUSTED_DOMAINS:
                return TrustedDomains;
            case INTERNAL_ONLY:
                return InternalOnly;
            default:
                logger.error( "Unrecognized domain sharing policy!" );
                return null;
        }
    }

    @JsonValue
    public String toString() {
        return domainSharingPolicy;
    }
}
