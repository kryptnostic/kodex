package com.kryptnostic.directory.v1.model;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.kryptnostic.directory.v1.exception.AlreadyApprovedException;
import com.kryptnostic.directory.v1.exception.AlreadyDeniedException;
import com.kryptnostic.directory.v1.exception.AlreadyOpenException;
import com.kryptnostic.directory.v1.model.request.DeveloperRegistrationRequest;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Model for Developer registrations.
 *
 * @author Nick Hewitt
 *
 */
public final class DeveloperRegistration extends DeveloperRegistrationRequest {
    private final RequestStatus status;

    @JsonCreator
    public DeveloperRegistration(
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.USERNAME_FIELD ) String username,
            @JsonProperty( Names.PASSWORD_FIELD ) String password,
            @JsonProperty( Names.CERTIFICATE_PROPERTY ) byte[] certificate,
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.GIVEN_NAME_FIELD ) String givenName,
            @JsonProperty( Names.FAMILY_NAME_FIELD ) Optional<String> familyName,
            @JsonProperty( Names.ORGANIZATION_FIELD ) Optional<String> organization,
            @JsonProperty( Names.ADDRESS_FIELD ) Optional<String> address,
            @JsonProperty( Names.STATE_FIELD ) Optional<String> state,
            @JsonProperty( Names.COUNTRY_FIELD ) Optional<String> country,
            @JsonProperty( Names.ZIPCODE_FIELD ) Optional<Integer> zipCode,
            @JsonProperty( Names.ORGANIZATION_SIZE_FIELD ) Optional<Integer> organizationSize,
            @JsonProperty( Names.USE_CASE_FIELD ) Optional<String> primaryUseCase,
            @JsonProperty( Names.BUSINESS_TYPE_FIELD ) Optional<String> businessType,
            @JsonProperty( Names.EXPECTED_NUMBER_OF_USER_FIELD ) Optional<Integer> expectedNumberOfUsers,
            @JsonProperty( Names.TIER_FIELD ) Optional<Integer> tier,
            @JsonProperty( Names.REASON_FIELD ) Optional<String> reason,
            @JsonProperty( Names.STATUS_FIELD ) RequestStatus status ) {
        super(
                realm,
                username,
                Optional.of( password ),
                certificate,
                email,
                givenName,
                familyName,
                organization,
                address,
                state,
                country,
                zipCode,
                organizationSize,
                primaryUseCase,
                businessType,
                expectedNumberOfUsers,
                tier,
                reason );

        this.status = status;
    }

    private DeveloperRegistration( RegistrationBuilder builder ) {
        super(
                builder.realm,
                builder.username,
                Optional.of( builder.password ),
                builder.certificate,
                builder.email,
                builder.givenName,
                Optional.fromNullable( builder.familyName ),
                Optional.fromNullable( builder.organization ),
                Optional.fromNullable( builder.address ),
                Optional.fromNullable( builder.state ),
                Optional.fromNullable( builder.country ),
                Optional.fromNullable( builder.zipCode ),
                Optional.fromNullable( builder.organizationSize ),
                Optional.fromNullable( builder.primaryUseCase ),
                Optional.fromNullable( builder.businessType ),
                Optional.fromNullable( builder.expectedNumberOfUsers ),
                Optional.fromNullable( builder.tier ),
                Optional.fromNullable( builder.reason ) );
        this.status = builder.status;
    }

    // TOOD: Remove this. RequestStatus is setup to be serialized correctly as int via Jackson annotations.
    /**
     * Gets the integer representation of the status for lookup.
     *
     * @return
     */
    @JsonIgnore
    public int getIntStatus() {
        return status.getValue();
    }

    @JsonProperty( Names.STATUS_FIELD )
    public RequestStatus getStatus() {
        return status;
    }

    @JsonIgnore
    public DeveloperRegistration approve() throws AlreadyApprovedException {
        if ( this.status.equals( RequestStatus.APPROVED ) ) {
            throw new AlreadyApprovedException();
        }
        return new DeveloperRegistration.RegistrationBuilder( this ).withStatus( RequestStatus.APPROVED ).build();
    }

    @JsonIgnore
    public DeveloperRegistration deny() throws AlreadyApprovedException, AlreadyDeniedException {
        if ( this.status.equals( RequestStatus.APPROVED ) ) {
            throw new AlreadyApprovedException();
        }
        if ( this.status.equals( RequestStatus.DENIED ) ) {
            throw new AlreadyDeniedException();
        }
        return new DeveloperRegistration.RegistrationBuilder( this ).withStatus( RequestStatus.DENIED ).build();
    }

    @JsonIgnore
    public DeveloperRegistration open() throws AlreadyApprovedException, AlreadyOpenException {
        if ( this.status.equals( RequestStatus.APPROVED ) ) {
            throw new AlreadyApprovedException();
        }
        if ( this.status.equals( RequestStatus.DENIED ) ) {
            throw new AlreadyOpenException();
        }
        return new DeveloperRegistration.RegistrationBuilder( this ).withStatus( RequestStatus.OPEN ).build();
    }

    public static class RegistrationBuilder {
        private UUID          registrationId;
        private String        realm;
        private String        username;
        private byte[]        certificate;
        private String        email;
        private String        givenName;
        private RequestStatus status;

        private String        familyName;
        private String        organization;
        private String        address;
        private String        state;
        private String        country;
        private Integer       zipCode;
        private Integer       organizationSize;
        private String        primaryUseCase;
        private String        businessType;
        private Integer       expectedNumberOfUsers;
        private Integer       tier;
        private String        reason;
        private String        password;

        public RegistrationBuilder( DeveloperRegistrationRequest request ) {
            this.realm = request.getRealm();
            this.username = request.getUsername();
            this.certificate = request.getCertificate();
            this.email = request.getEmail();
            this.givenName = request.getGivenName();
            this.familyName = request.getFamilyName().orNull();
            this.organization = request.getOrganization().orNull();
            this.address = request.getAddress().orNull();
            this.state = request.getState().orNull();
            this.country = request.getCountry().orNull();
            this.zipCode = request.getZipCode().orNull();
            this.organizationSize = request.getOrganizationSize().orNull();
            this.primaryUseCase = request.getPrimaryUseCase().orNull();
            this.businessType = request.getBusinessType().orNull();
            this.expectedNumberOfUsers = request.getExpectedNumberOfUsers().orNull();
            this.tier = request.getTier().orNull();
            this.reason = request.getReason().orNull();
            this.password = request.getPassword();
       }

        public RegistrationBuilder withRegistrationId( UUID registrationId ) {
            this.registrationId = registrationId;
            return this;
        }

        public RegistrationBuilder withStatus( RequestStatus status ) {
            this.status = status;
            return this;
        }

        public DeveloperRegistration build() {
            Preconditions.checkNotNull( certificate );
            Preconditions.checkNotNull( status );
            Preconditions.checkArgument( StringUtils.isNotBlank( realm ) );
            Preconditions.checkArgument( StringUtils.isNotBlank( username ) );
            Preconditions.checkArgument( StringUtils.isNotBlank( email ) );
            Preconditions.checkArgument( StringUtils.isNotBlank( givenName ) );
            Preconditions.checkArgument( StringUtils.isNotBlank( password ) );
            return new DeveloperRegistration( this );
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
        result = prime * result + ( ( password == null ) ? 0 : password.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( !super.equals( obj ) ) return false;
        if ( getClass() != obj.getClass() ) return false;
        DeveloperRegistration other = (DeveloperRegistration) obj;
        if ( status != other.status ) return false;
        if ( password == null ) {
            if ( other.password != null ) return false;
        } else if ( !password.equals( other.password ) ) return false;
        return true;
    }

}
