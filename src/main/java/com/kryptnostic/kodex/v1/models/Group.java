package com.kryptnostic.kodex.v1.models;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.directory.v1.principal.Principal;

public interface Group extends Principal {
    String getName();

    Set<UUID> getGroups();

    Set<UUID> getUsers();
}
