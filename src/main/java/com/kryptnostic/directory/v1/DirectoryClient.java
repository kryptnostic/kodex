package com.kryptnostic.directory.v1;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.directory.v1.model.response.PublicKeyEnvelope;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.sharing.v1.models.NotificationPreference;

public interface DirectoryClient {
    Set<UUID> listUserInRealm( String realm );

    PublicKeyEnvelope getPublicKey( UUID id ) throws ResourceNotFoundException;

    NotificationPreference getNotificationPreference();

    void setNotificationPreference( NotificationPreference preference );
}
