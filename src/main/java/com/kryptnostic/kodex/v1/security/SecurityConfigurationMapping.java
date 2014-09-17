package com.kryptnostic.kodex.v1.security;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class SecurityConfigurationMapping {

    private final Table<Class, Class, Object> table;

    public SecurityConfigurationMapping() {
        table = HashBasedTable.create();
    }

    public SecurityConfigurationMapping add(Class encryptableClass, Object value) {
        if (value != null) {
            table.put(encryptableClass, value.getClass(), value);
        }
        return this;
    }

    public <T> T remove(Class encryptableClass, Class<T> valueClass) {
        return (T) table.remove(encryptableClass, valueClass);
    }

    public <T> T get(Class encryptableClass, Class<T> valueClass) {
        return (T) table.get(encryptableClass, valueClass);
    }

    public boolean contains(Class encryptableClass, Class valueClass) {
        return table.contains(encryptableClass, valueClass);
    }
}
