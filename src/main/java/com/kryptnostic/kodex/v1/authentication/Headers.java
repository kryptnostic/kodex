package com.kryptnostic.kodex.v1.authentication;


public enum Headers {
    X_CSRF_TOKEN(HeaderNames.CSRF_TOKEN),
    PRINCIPAL(HeaderNames.PRINCIPAL),
    CREDENTIAL(HeaderNames.CREDENTIAL);
    
    private final String header;
    
    private Headers( String header ) {
        this.header = header;
    }
    
    public String toString() {
        return header;
    }
}
