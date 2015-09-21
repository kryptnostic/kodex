package com.kryptnostic.kodex.v1.models;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class KryptnosticGroup implements Group {
    private final UUID      id;
    private final String    name;
    private final Set<UUID> groups;
    private final Set<UUID> users;

    public KryptnosticGroup(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.NAME_FIELD ) String name,
            @JsonProperty( Names.GROUP_PROPERTY ) Set<UUID> groups,
            @JsonProperty( Names.USERS_FIELD ) Set<UUID> users ) {
        this.id = id;
        this.name = name;
        this.groups = groups;
        this.users = users;
    }

    @Override
    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @Override
    @JsonProperty( Names.NAME_FIELD )
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty( Names.GROUP_PROPERTY )
    public Set<UUID> getGroups() {
        return groups;
    }

    @Override
    @JsonProperty( Names.USERS_FIELD )
    public Set<UUID> getUsers() {
        return users;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( groups == null ) ? 0 : groups.hashCode() );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( users == null ) ? 0 : users.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof KryptnosticGroup ) ) {
            return false;
        }
        KryptnosticGroup other = (KryptnosticGroup) obj;
        if ( groups == null ) {
            if ( other.groups != null ) {
                return false;
            }
        } else if ( !groups.equals( other.groups ) ) {
            return false;
        }
        if ( id == null ) {
            if ( other.id != null ) {
                return false;
            }
        } else if ( !id.equals( other.id ) ) {
            return false;
        }
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        } else if ( !name.equals( other.name ) ) {
            return false;
        }
        if ( users == null ) {
            if ( other.users != null ) {
                return false;
            }
        } else if ( !users.equals( other.users ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KryptnosticGroup [id=" + id + ", name=" + name + ", groups=" + groups + ", users=" + users + "]";
    }

}
