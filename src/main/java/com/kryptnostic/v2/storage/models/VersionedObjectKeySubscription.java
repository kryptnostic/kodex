package com.kryptnostic.v2.storage.models;

import com.kryptnostic.v2.events.models.SubscriptionType;

public class VersionedObjectKeySubscription {
    private final VersionedObjectKey objectKey;
    private final SubscriptionType   subscriptionType;

    public VersionedObjectKeySubscription( VersionedObjectKey objectKey, SubscriptionType subscriptionType ) {
        this.objectKey = objectKey;
        this.subscriptionType = subscriptionType;
    }

    public VersionedObjectKey getObjectKey() {
        return objectKey;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectKey == null ) ? 0 : objectKey.hashCode() );
        result = prime * result + ( ( subscriptionType == null ) ? 0 : subscriptionType.hashCode() );
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
        if ( !( obj instanceof VersionedObjectKeySubscription ) ) {
            return false;
        }
        VersionedObjectKeySubscription other = (VersionedObjectKeySubscription) obj;
        if ( objectKey == null ) {
            if ( other.objectKey != null ) {
                return false;
            }
        } else if ( !objectKey.equals( other.objectKey ) ) {
            return false;
        }
        if ( subscriptionType != other.subscriptionType ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VersionedObjectKeySubscription [objectKey=" + objectKey + ", subscriptionType=" + subscriptionType
                + "]";
    }

}
