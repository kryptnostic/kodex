package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.directory.v1.http.UserDirectoryApi;
import com.kryptnostic.sharing.v1.http.SharingApi;

/**
 * This provides interfaces to Kryptnostic server-side services
 */
public interface KryptnosticServicesFactory {

    SharingApi createSharingApi();

    UserDirectoryApi createDirectoryApi();

}
