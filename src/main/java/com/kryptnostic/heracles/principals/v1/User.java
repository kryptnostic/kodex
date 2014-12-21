package com.kryptnostic.heracles.principals.v1;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kryptnostic.directory.v1.models.Principal;

/**
 * Interface for user principals
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface User extends Principal {
    String getId();
    
    @Nullable
    String getEmail();
    
    @Nullable 
    byte[] getPassword();
    
    @Nullable
    byte[] getCertificate();
    
    Set<GroupKey> getGroups();
    Map<String, String> getAttributes();
    String getAttribute( String key );
    
    User withoutPassword();
}
