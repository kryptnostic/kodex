package com.kryptnostic.kodex.v1.exceptions.types;

@SuppressWarnings("serial")
public class SecurityConfigurationException extends Exception {

    public SecurityConfigurationException() {

    }

    public SecurityConfigurationException(String msg) {
        super(msg);
    }

    public SecurityConfigurationException(String msg, Throwable e) {
        super(msg, e);
    }
}
