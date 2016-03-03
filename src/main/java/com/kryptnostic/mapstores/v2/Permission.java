package com.kryptnostic.mapstores.v2;

/**
 * NOTE: DO NOT EVER RE-ORDER THESE ENTRIES. We currently rely on .ordinal and .values() to serialize and changing the
 * order changes these values.
 * 
 * @author Drew Bailey drew@kryptnostic.com
 */
public enum Permission {
    READ, WRITE, OWNER;
}
