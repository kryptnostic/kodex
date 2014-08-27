package com.kryptnostic.storage.v1.models.response;

import java.util.HashMap;
import java.util.Map;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.kodex.v1.models.response.ResponseKey;

public class DocumentResponse extends BasicResponse<Map<String, String>> {

    public DocumentResponse() {
        data = new HashMap<String, String>();
    }

    public DocumentResponse(String document, int status, boolean success) {
        super(new HashMap<String, String>(), status, success);
        data.put(ResponseKey.DOCUMENT_KEY, document);
    }

}
