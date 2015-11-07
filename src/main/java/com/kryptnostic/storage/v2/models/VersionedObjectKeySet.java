package com.kryptnostic.storage.v2.models;

import java.util.Collection;
import java.util.HashSet;

public class VersionedObjectKeySet extends HashSet<VersionedObjectKey>{

    private static final long serialVersionUID = -2280678803539884513L;

    public VersionedObjectKeySet() {
        super();
    }

    public VersionedObjectKeySet( Collection<? extends VersionedObjectKey> c ) {
        super( c );
    }
    
    public VersionedObjectKeySet( int i ) {
        super( i );
    }

    public VersionedObjectKeySet( VersionedObjectKey key ) {
        this( 1 );
        add( key );
    }

}
