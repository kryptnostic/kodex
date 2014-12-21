package com.kryptnostic.heracles.principals.v1;

import java.util.Set;

import com.kryptnostic.directory.v1.models.Principal;

public interface Group extends Principal {
    Set<Group> getGroups();
    Set<User> getUsers();
}
