package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

@Immutable
public class ObjectTreeLoadResponse {
    private final Map<UUID, ObjectMetadataEncryptedNode> omTree;
    private final Optional<String>                       nextPage;
    private static final String                          template = "/levels/%s/%d/%s/%d";

    @JsonCreator
    public ObjectTreeLoadResponse(
            @JsonProperty( "objectMetadataTree" ) Map<UUID, ObjectMetadataEncryptedNode> results,
            @JsonProperty( "scrollUp" ) Optional<String> nextPage) {
        this.omTree = results;
        this.nextPage = nextPage;
    }

    public static String nextPage(
            UUID aclId,
            int pageSize,
            VersionedObjectKey latestObject ) {
        return String.format( template,
                aclId.toString(),
                pageSize,
                latestObject.getObjectId().toString(),
                latestObject.getVersion() );
    }

    @JsonProperty( "objectMetadataTree" )
    public Map<UUID, ObjectMetadataEncryptedNode> getOmTree() {
        return omTree;
    }

    @JsonProperty( "scrollUp" )
    public Optional<String> getNextPage() {
        return nextPage;
    }

}
