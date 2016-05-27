package com.kryptnostic.v2.tree.models;

import java.util.Map;
import java.util.UUID;

import javax.annotation.CheckForNull;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.storage.models.ObjectMetadataEncryptedNode;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

@Immutable
public class ObjectTreeTraversalResponse {
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

    @CheckForNull
    public static String nextPage(
            UUID aclId,
            Integer pageSize,
            VersionedObjectKey latestObject ) {
        if ( latestObject == null || aclId == null || pageSize == null ) {
            return null;
        }
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
