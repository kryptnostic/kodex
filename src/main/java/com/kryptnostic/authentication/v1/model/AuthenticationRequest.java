package com.kryptnostic.authentication.v1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class AuthenticationRequest extends UserKey {
    private static final long serialVersionUID = 82608351393994618L;
    private final byte[]      password;

    @JsonCreator
    public AuthenticationRequest(
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.NAME_FIELD ) String name,
            @JsonProperty( Names.PASSWORD_FIELD ) byte[] password ) {
        super( realm, name );
        this.password = password;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public byte[] getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest [password=*******" + ", name=" + name + ", realm=" + realm + "]";
    }
}
