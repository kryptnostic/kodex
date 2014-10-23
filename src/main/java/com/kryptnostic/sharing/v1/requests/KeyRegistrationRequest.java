package com.kryptnostic.sharing.v1.requests;

import java.util.List;

import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

public class KeyRegistrationRequest {
    List<EncryptedSearchDocumentKey> searchKey;
    List<DocumentId> documentId;
}
