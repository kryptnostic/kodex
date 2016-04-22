package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

@Immutable
public class ObjectTreeLoadResponse {
    private final Map<UUID, ObjectMetadataEncryptedNode> omTrees;
    private final Optional<String>                       nextPage;
    private static final String                          template = "/levels/%s/%d/%s/%d";

    @JsonCreator
    public ObjectTreeLoadResponse(
            @JsonProperty( "objectMetadataTrees" ) Map<UUID, ObjectMetadataEncryptedNode> results,
            @JsonProperty( "scrollUp" ) Optional<String> nextPage) {
        this.omTrees = results;
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

    @JsonProperty( "objectMetadataTrees" )
    public Map<UUID, ObjectMetadataEncryptedNode> getOmTrees() {
        return omTrees;
    }

    @JsonProperty( "scrollUp" )
    public Optional<String> getNextPage() {
        return nextPage;
    }

}
