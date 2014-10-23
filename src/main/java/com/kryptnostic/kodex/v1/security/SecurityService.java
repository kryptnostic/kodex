package com.kryptnostic.kodex.v1.security;

import com.kryptnostic.users.v1.UserKey;

public interface SecurityService {
    public SecurityConfigurationMapping getSecurityConfigurationMapping();

    public String getUserCredential();

    public UserKey getUserKey();
}
