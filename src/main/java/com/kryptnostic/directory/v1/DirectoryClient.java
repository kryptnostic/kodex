package com.kryptnostic.directory.v1;

import java.util.Set;

import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.directory.v1.models.response.PublicKeyEnvelope;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.sharing.v1.models.NotificationPreference;

public interface DirectoryClient {
    Set<UserKey> listUserInRealm( String realm );

    PublicKeyEnvelope getPublicKey( String username ) throws ResourceNotFoundException;

    NotificationPreference getNotificationPreference();

    void setNotificationPreference( NotificationPreference preference );
}
