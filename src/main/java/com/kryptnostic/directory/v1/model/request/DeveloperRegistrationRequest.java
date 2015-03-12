package com.kryptnostic.directory.v1.model.request;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

/**
 * Request to create a developer registration.
 * 
 * @author Nick Hewitt
 *
 */
public class DeveloperRegistrationRequest {
    private final String            realm;
    private final String            username;
    private final String            password;
    private final BlockCiphertext   encryptedSalt;
    private final byte[]            certificate;
    private final String            email;
    private final String            givenName;

    private final Optional<String>  familyName;
    private final Optional<String>  organization;
    private final Optional<String>  address;
    private final Optional<String>  state;
    private final Optional<String>  country;
    private final Optional<Integer> zipCode;
    private final Optional<Integer> organizationSize;
    private final Optional<String>  primaryUseCase;
    private final Optional<String>  businessType;
    private final Optional<Integer> expectedNumberOfUsers;
    private final Optional<Integer> tier;
    private final Optional<String>  reason;

    @JsonCreator
    public DeveloperRegistrationRequest(
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.NAME_FIELD ) String username,
            @JsonProperty( Names.PASSWORD_FIELD ) String password,
            @JsonProperty( Names.ENCRYPTED_SALT_FIELD ) BlockCiphertext encryptedSalt,
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
            @JsonProperty( Names.REASON_FIELD ) Optional<String> reason ) {
        this.realm = realm;
        this.username = username;
        this.password = password;
        this.encryptedSalt = encryptedSalt;
        this.certificate = certificate;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.organization = organization;
        this.address = address;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.organizationSize = organizationSize;
        this.primaryUseCase = primaryUseCase;
        this.businessType = businessType;
        this.expectedNumberOfUsers = expectedNumberOfUsers;
        this.tier = tier;
        this.reason = reason;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getRealm() {
        return realm;
    }

    @JsonProperty( Names.NAME_FIELD )
    public String getUsername() {
        return username;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @JsonProperty( Names.ENCRYPTED_SALT_FIELD )
    public BlockCiphertext getEncryptedSalt() {
        return encryptedSalt;
    }

    @JsonProperty( Names.CERTIFICATE_PROPERTY )
    public byte[] getCertificate() {
        return certificate;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.GIVEN_NAME_FIELD )
    public String getGivenName() {
        return givenName;
    }

    @JsonProperty( Names.FAMILY_NAME_FIELD )
    public Optional<String> getFamilyName() {
        return familyName;
    }

    @JsonProperty( Names.ORGANIZATION_FIELD )
    public Optional<String> getOrganization() {
        return organization;
    }

    @JsonProperty( Names.ORGANIZATION_SIZE_FIELD )
    public Optional<Integer> getOrganizationSize() {
        return organizationSize;
    }

    @JsonProperty( Names.USE_CASE_FIELD )
    public Optional<String> getPrimaryUseCase() {
        return primaryUseCase;
    }

    @JsonProperty( Names.BUSINESS_TYPE_FIELD )
    public Optional<String> getBusinessType() {
        return businessType;
    }

    @JsonProperty( Names.EXPECTED_NUMBER_OF_USER_FIELD )
    public Optional<Integer> getExpectedNumberOfUsers() {
        return expectedNumberOfUsers;
    }

    @JsonProperty( Names.TIER_FIELD )
    public Optional<Integer> getTier() {
        return tier;
    }

    @JsonProperty( Names.ADDRESS_FIELD )
    public Optional<String> getAddress() {
        return address;
    }

    @JsonProperty( Names.STATE_FIELD )
    public Optional<String> getState() {
        return state;
    }

    @JsonProperty( Names.COUNTRY_FIELD )
    public Optional<String> getCountry() {
        return country;
    }

    @JsonProperty( Names.ZIPCODE_FIELD )
    public Optional<Integer> getZipCode() {
        return zipCode;
    }

    @JsonProperty( Names.REASON_FIELD )
    public Optional<String> getReason() {
        return reason;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( address == null ) ? 0 : address.hashCode() );
        result = prime * result + ( ( businessType == null ) ? 0 : businessType.hashCode() );
        result = prime * result + Arrays.hashCode( certificate );
        result = prime * result + ( ( country == null ) ? 0 : country.hashCode() );
        result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
        result = prime * result + ( ( expectedNumberOfUsers == null ) ? 0 : expectedNumberOfUsers.hashCode() );
        result = prime * result + ( ( familyName == null ) ? 0 : familyName.hashCode() );
        result = prime * result + ( ( givenName == null ) ? 0 : givenName.hashCode() );
        result = prime * result + ( ( organization == null ) ? 0 : organization.hashCode() );
        result = prime * result + ( ( organizationSize == null ) ? 0 : organizationSize.hashCode() );
        result = prime * result + ( ( password == null ) ? 0 : password.hashCode() );
        result = prime * result + ( ( primaryUseCase == null ) ? 0 : primaryUseCase.hashCode() );
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
        result = prime * result + ( ( reason == null ) ? 0 : reason.hashCode() );
        result = prime * result + ( ( state == null ) ? 0 : state.hashCode() );
        result = prime * result + ( ( tier == null ) ? 0 : tier.hashCode() );
        result = prime * result + ( ( username == null ) ? 0 : username.hashCode() );
        result = prime * result + ( ( zipCode == null ) ? 0 : zipCode.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        DeveloperRegistrationRequest other = (DeveloperRegistrationRequest) obj;
        if ( address == null ) {
            if ( other.address != null ) return false;
        } else if ( !address.equals( other.address ) ) return false;
        if ( businessType == null ) {
            if ( other.businessType != null ) return false;
        } else if ( !businessType.equals( other.businessType ) ) return false;
        if ( !Arrays.equals( certificate, other.certificate ) ) return false;
        if ( country == null ) {
            if ( other.country != null ) return false;
        } else if ( !country.equals( other.country ) ) return false;
        if ( email == null ) {
            if ( other.email != null ) return false;
        } else if ( !email.equals( other.email ) ) return false;
        if ( expectedNumberOfUsers == null ) {
            if ( other.expectedNumberOfUsers != null ) return false;
        } else if ( !expectedNumberOfUsers.equals( other.expectedNumberOfUsers ) ) return false;
        if ( familyName == null ) {
            if ( other.familyName != null ) return false;
        } else if ( !familyName.equals( other.familyName ) ) return false;
        if ( givenName == null ) {
            if ( other.givenName != null ) return false;
        } else if ( !givenName.equals( other.givenName ) ) return false;
        if ( organization == null ) {
            if ( other.organization != null ) return false;
        } else if ( !organization.equals( other.organization ) ) return false;
        if ( organizationSize == null ) {
            if ( other.organizationSize != null ) return false;
        } else if ( !organizationSize.equals( other.organizationSize ) ) return false;
        if ( password == null ) {
            if ( other.password != null ) return false;
        } else if ( !password.equals( other.password ) ) return false;
        if ( primaryUseCase == null ) {
            if ( other.primaryUseCase != null ) return false;
        } else if ( !primaryUseCase.equals( other.primaryUseCase ) ) return false;
        if ( realm == null ) {
            if ( other.realm != null ) return false;
        } else if ( !realm.equals( other.realm ) ) return false;
        if ( reason == null ) {
            if ( other.reason != null ) return false;
        } else if ( !reason.equals( other.reason ) ) return false;
        if ( state == null ) {
            if ( other.state != null ) return false;
        } else if ( !state.equals( other.state ) ) return false;
        if ( tier == null ) {
            if ( other.tier != null ) return false;
        } else if ( !tier.equals( other.tier ) ) return false;
        if ( username == null ) {
            if ( other.username != null ) return false;
        } else if ( !username.equals( other.username ) ) return false;
        if ( zipCode == null ) {
            if ( other.zipCode != null ) return false;
        } else if ( !zipCode.equals( other.zipCode ) ) return false;
        return true;
    }

}
