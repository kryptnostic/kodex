package com.kryptnostic.heracles.registration.v1.requests;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.heracles.principals.v1.requests.UserRegistrationRequest;
import com.kryptnostic.kodex.v1.constants.Names;

public class DeveloperRegistrationRequest implements RegistrationRequest,Serializable{
    private static final long serialVersionUID = 9016999278273387252L;
    private RequestStatus status;
    private final UserRegistrationRequest userCreationRequest;
    private final String realm;
    private final String organization;
    private final String address;
    private final String state;
    private final String country;
    private final int zipCode;
    private final int organizationSize;
    private final String primaryUseCase; 
    private final String businessType;
    private final int expectedNumberOfUsers;
    private final int tier;
    private final String reason;
    
    public DeveloperRegistrationRequest( 
            @JsonProperty(Names.USER_FIELD) UserRegistrationRequest userCreationRequest,
            @JsonProperty(Names.REALM_FIELD) String realm,
            @JsonProperty(Names.ORGANIZATION_FIELD) String organization,
            @JsonProperty(Names.ADDRESS_FIELD) String address,
            @JsonProperty(Names.STATE_FIELD) String state, 
            @JsonProperty(Names.COUNTRY_FIELD) String country,
            @JsonProperty(Names.ZIPCODE_FIELD) int zipCode,
            @JsonProperty(Names.ORGANIZATION_SIZE_FIELD) int organizationSize,
            @JsonProperty(Names.USE_CASE_FIELD) String primaryUseCase,
            @JsonProperty(Names.BUSINESS_TYPE_FIELD) String businessType,
            @JsonProperty(Names.EXPECTED_NUMBER_OF_USER_FIELD) int expectedNumberOfUsers,
            @JsonProperty(Names.TIER_FIELD) int tier,
            @JsonProperty(Names.STATUS_FIELD) Optional<RequestStatus> status,
            @JsonProperty(Names.REASON_FIELD) Optional<String> reason
            ) {
        this.status = status.or( RequestStatus.OPEN );
        this.userCreationRequest = userCreationRequest;
        this.realm = realm;
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
        this.reason = reason.orNull();
    }
    
    @JsonProperty(Names.STATUS_FIELD)
    public RequestStatus getStatus() {
        return status;
    }
    
    /**
     * Gets the integer representation of the status for lookup.  
     * @return
     */
    @JsonIgnore
    public int getIntStatus() {
        return status.getValue();
    }

    @JsonProperty(Names.USER_FIELD) 
    public UserRegistrationRequest getUserCreationRequest() {
        return userCreationRequest;
    }

    @JsonProperty(Names.REALM_FIELD) 
    public String getRealm() {
        return realm;
    }

    @JsonProperty(Names.ORGANIZATION_FIELD) 
    public String getOrganization() {
        return organization;
    }
    
    @JsonProperty(Names.ORGANIZATION_SIZE_FIELD) 
    public int getOrganizationSize() {
        return organizationSize;
    }

    @JsonProperty(Names.USE_CASE_FIELD) 
    public String getPrimaryUseCase() {
        return primaryUseCase;
    }

    @JsonProperty(Names.BUSINESS_TYPE_FIELD) 
    public String getBusinessType() {
        return businessType;
    }

    @JsonProperty(Names.EXPECTED_NUMBER_OF_USER_FIELD) 
    public int getExpectedNumberOfUsers() {
        return expectedNumberOfUsers;
    }

    @JsonProperty(Names.TIER_FIELD) 
    public int getTier() {
        return tier;
    }

    @JsonProperty(Names.ADDRESS_FIELD) 
    public String getAddress() {
        return address;
    }

    @JsonProperty(Names.STATE_FIELD) 
    public String getState() {
        return state;
    }

    @JsonProperty(Names.COUNTRY_FIELD) 
    public String getCountry() {
        return country;
    }

    @JsonProperty(Names.ZIPCODE_FIELD) 
    public int getZipCode() {
        return zipCode;
    }

    @JsonProperty(Names.REASON_FIELD) 
    public String getReason() {
        return reason;
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
    }
    
    public void deny() {
        this.status = RequestStatus.DENIED;
    }
    
    public void open() {
        this.status = RequestStatus.OPEN;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( address == null ) ? 0 : address.hashCode() );
        result = prime * result + ( ( businessType == null ) ? 0 : businessType.hashCode() );
        result = prime * result + ( ( country == null ) ? 0 : country.hashCode() );
        result = prime * result + expectedNumberOfUsers;
        result = prime * result + ( ( organization == null ) ? 0 : organization.hashCode() );
        result = prime * result + organizationSize;
        result = prime * result + ( ( primaryUseCase == null ) ? 0 : primaryUseCase.hashCode() );
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
        result = prime * result + ( ( reason == null ) ? 0 : reason.hashCode() );
        result = prime * result + ( ( state == null ) ? 0 : state.hashCode() );
        result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
        result = prime * result + tier;
        result = prime * result + ( ( userCreationRequest == null ) ? 0 : userCreationRequest.hashCode() );
        result = prime * result + zipCode;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!( obj instanceof DeveloperRegistrationRequest )) {
            return false;
        }
        DeveloperRegistrationRequest other = (DeveloperRegistrationRequest) obj;
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals( other.address )) {
            return false;
        }
        if (businessType == null) {
            if (other.businessType != null) {
                return false;
            }
        } else if (!businessType.equals( other.businessType )) {
            return false;
        }
        if (country == null) {
            if (other.country != null) {
                return false;
            }
        } else if (!country.equals( other.country )) {
            return false;
        }
        if (expectedNumberOfUsers != other.expectedNumberOfUsers) {
            return false;
        }
        if (organization == null) {
            if (other.organization != null) {
                return false;
            }
        } else if (!organization.equals( other.organization )) {
            return false;
        }
        if (organizationSize != other.organizationSize) {
            return false;
        }
        if (primaryUseCase == null) {
            if (other.primaryUseCase != null) {
                return false;
            }
        } else if (!primaryUseCase.equals( other.primaryUseCase )) {
            return false;
        }
        if (realm == null) {
            if (other.realm != null) {
                return false;
            }
        } else if (!realm.equals( other.realm )) {
            return false;
        }
        if (reason == null) {
            if (other.reason != null) {
                return false;
            }
        } else if (!reason.equals( other.reason )) {
            return false;
        }
        if (state == null) {
            if (other.state != null) {
                return false;
            }
        } else if (!state.equals( other.state )) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        if (tier != other.tier) {
            return false;
        }
        if (userCreationRequest == null) {
            if (other.userCreationRequest != null) {
                return false;
            }
        } else if (!userCreationRequest.equals( other.userCreationRequest )) {
            return false;
        }
        if (zipCode != other.zipCode) {
            return false;
        }
        return true;
    }

}
