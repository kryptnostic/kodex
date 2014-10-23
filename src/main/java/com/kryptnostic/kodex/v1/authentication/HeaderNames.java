package com.kryptnostic.kodex.v1.authentication;

public final class HeaderNames {
    private HeaderNames() {}
    
    public static final String CSRF_TOKEN = "X-CSRF-TOKEN";
    public static final String PRINCIPAL = "X-Kryptnostic-Principal";
    public static final String CREDENTIAL = "X-Kryptnostic-Credential";
}
