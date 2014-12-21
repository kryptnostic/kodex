package com.kryptnostic.heracles.principals.v1;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.PASSWORD_FIELD;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.models.UserKey;

public class HeraclesUser extends UserKey implements User,Serializable {
    private static final long serialVersionUID = 3581755283203968675L;
    private final String email;
    private final byte[] password;
    private final byte[] certificate;
    private final Set<GroupKey> groups = Sets.newConcurrentHashSet();
    private final Map<String, String> attributes = Maps.newConcurrentMap();
    
    @JsonCreator
    public HeraclesUser(
            @JsonProperty(REALM_FIELD) String realm,
            @JsonProperty(NAME_FIELD) String name,
            @JsonProperty(EMAIL_FIELD) String email,
            @JsonProperty(PASSWORD_FIELD) Optional<byte[]> password,
            @JsonProperty(CERTIFICATE_PROPERTY) Optional<byte[]> certificate,
            @JsonProperty(GROUPS_PROPERTY) Set<GroupKey> groups,
            @JsonProperty(ATTRIBUTES_FIELD) Map<String, String> attributes) {
        super( realm , name );
        this.groups.addAll( groups );
        this.attributes.putAll( attributes );
        this.password = password.or( new byte[0] );
        this.certificate = certificate.or(  new byte[0] );
        this.email = email;
    }

    public HeraclesUser(String realm, String name ) {
        this( realm , name , null );
    }
    
    public HeraclesUser( String realm, String name, String email ) {
        super( realm , name );
        this.certificate = this.password = new byte[0];
        this.email = email;
    }
     
    @JsonIgnore
    public String getId() {
        return UserKey.buildFqn( realm , name );
    }

    @JsonProperty(EMAIL_FIELD)
    public String getEmail() {
        return email;
    }
    
    @JsonProperty(PASSWORD_FIELD) 
    public byte[] getPassword() {
        return password;
    }
    
    @JsonProperty(CERTIFICATE_PROPERTY)
    public byte[] getCertificate() {
        return certificate;
    }

    @Override
    @JsonProperty(ATTRIBUTES_FIELD)
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    @JsonProperty(GROUPS_PROPERTY)
    public Set<GroupKey> getGroups() {
        return groups;
    }
    
    @Override
    public String getAttribute(String key) {
        return attributes.get( key );
    }

    public User withoutPassword() {
        return new HeraclesUser( realm , name , email , Optional.<byte[]>absent() , Optional.of( certificate ) , groups , attributes );
    }
    
    @Override
    public String toString() {
        return "HeraclesUser [password=" + Arrays.toString( password ) + ", certificate="
                + Arrays.toString( certificate ) + ", groups=" + groups + ", attributes=" + attributes + "]";
    }
}
