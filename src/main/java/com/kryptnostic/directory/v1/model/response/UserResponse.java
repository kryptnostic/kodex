package com.kryptnostic.directory.v1.model.response;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Wrapper around User data to hide confidential account information.
 *
 * @author Nick Hewitt
 *
 */
public final class UserResponse {
    private final UUID                userId;
    private final String              realm;
    private final String              name;
    private final String              givenName;
    private final String              familyName;
    private final String              email;
    private final Set<UUID>       groups;
    private final Map<String, String> attributes;

    @JsonCreator
    public UserResponse(
            @JsonProperty( Names.ID_FIELD ) UUID userId,
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.USERNAME_FIELD ) String name,
            @JsonProperty( Names.GIVEN_NAME_FIELD ) String givenName,
            @JsonProperty( Names.FAMILY_NAME_FIELD ) String familyName,
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.GROUP_PROPERTY ) Set<UUID> groups,
            @JsonProperty( Names.ATTRIBUTES_FIELD ) Map<String, String> attributes ) {
        this.userId = userId;

        this.realm = realm;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.email = email;
        this.groups = groups;
        this.attributes = attributes;
    }

    public static UserResponse fromUser( User user ) {
        return new UserResponse(
                user.getId(),
                user.getRealm(),
                user.getName(),
                user.getGivenName().get(),
                user.getFamilyName().get(),
                user.getEmail(),
                user.getGroups(),
                user.getAttributes() );
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getUserId() {
        return userId;
    }

    @JsonProperty( Names.GIVEN_NAME_FIELD )
    public String getGivenName() {
        return givenName;
    }

    @JsonProperty( Names.FAMILY_NAME_FIELD )
    public String getFamilyName() {
        return familyName;
    }

    @JsonProperty( Names.USERNAME_FIELD )
    public String getName() {
        return name;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getRealm() {
        return realm;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.GROUP_PROPERTY )
    public Set<UUID> getGroups() {
        return groups;
    }

    @JsonProperty( Names.ATTRIBUTES_FIELD )
    public Map<String, String> getAttributes() {
        return attributes;
    }

}
