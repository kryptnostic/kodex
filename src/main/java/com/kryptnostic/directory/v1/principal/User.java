package com.kryptnostic.directory.v1.principal;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Interface for user principals
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class" )
public interface User extends Principal {
    String getId();

    @Nullable
    String getGivenName();

    @Nullable
    String getFamilyName();

    @Nullable
    String getEmail();

    @Nullable
    String getPassword();

    @Nullable
    byte[] getCertificate();

    Set<GroupKey> getGroups();

    Map<String, String> getAttributes();

    String getAttribute( String key );
}
