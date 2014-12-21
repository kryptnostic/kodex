package com.kryptnostic.heracles.authorization.v1;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public final class Permissions {
    private final EnumSet<Permission> permissions;
    
    public Permissions( Collection<Permission> permissions ) {
        this.permissions = EnumSet.copyOf( permissions );
    }

    public Set<Permission> get() {
        return permissions;
    }

    @JsonValue
    public int valueOf() {
        int value = 0;
        for( Permission permission : permissions ) {
            value|=permission.getValue();
        }
        return value;
    }
    
    @JsonCreator
    public static Permissions fromValue( int value ) {
        Collection<Permission> permissions = Sets.newHashSetWithExpectedSize( Integer.bitCount( value ) );
        
        //If no permission bits are set it is the equivalent of deny.
        if( value == 0 ) {
            return new Permissions( ImmutableSet.of( Permission.DENY ) );
        }
        
        //Unpack an integer's worth of permissions.
        for( int i = 0 ; i < Integer.SIZE ; ++i ) {
            int permissionBit = value & (1 << i);
            if( permissionBit != 0 ) {
                permissions.add( Permission.fromValue( permissionBit ) );
            }
        }
        
        return new Permissions( permissions );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( permissions == null ) ? 0 : permissions.hashCode() );
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
        if (!( obj instanceof Permissions )) {
            return false;
        }
        Permissions other = (Permissions) obj;
        if (permissions == null) {
            if (other.permissions != null) {
                return false;
            }
        } else if (!permissions.equals( other.permissions )) {
            return false;
        }
        return true;
    }
}
