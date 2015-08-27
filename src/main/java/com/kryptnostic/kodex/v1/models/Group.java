package com.kryptnostic.kodex.v1.models;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kryptnostic.directory.v1.principal.Principal;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class" )
public interface Group extends Principal {
    String getName();

    Set<UUID> getGroups();

    Set<UUID> getUsers();
}
