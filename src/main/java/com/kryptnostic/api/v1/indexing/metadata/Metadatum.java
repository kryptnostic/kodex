package com.kryptnostic.api.v1.indexing.metadata;

import java.util.List;

public interface Metadatum {
	String getDocumentId();
	String getToken();
	List<Integer> getLocations();
	boolean equals(Metadatum obj);
}
