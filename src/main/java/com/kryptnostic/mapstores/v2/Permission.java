package com.kryptnostic.mapstores.v2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public enum Permission {
    READ, WRITE, OWNER;

    public static void serializeSet( DataOutput out, Set<Permission> set ) throws IOException {
        for ( Permission permission : Permission.values() ) {
            out.writeBoolean( set.contains( permission ) );
        }
    }

    public static Set<Permission> deserializeSet( DataInput in) throws IOException {
        EnumSet<Permission> permissions = EnumSet.noneOf( Permission.class );
        if ( in.readBoolean() ) {
            permissions.add( READ );
        }
        if ( in.readBoolean() ) {
            permissions.add( WRITE );
        }
        if ( in.readBoolean() ) {
            permissions.add( OWNER );
        }
        return permissions;
    }
}
