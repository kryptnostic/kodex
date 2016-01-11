package com.kryptnostic.v2.sharing.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.kryptnostic.indexing.v1.ObjectSearchPair;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.sharing.models.RevocationRequest;
import com.kryptnostic.v2.sharing.models.Share;
import com.kryptnostic.v2.sharing.models.SharingRequest;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface SharingApi {

    String ID      = Names.ID_FIELD;
    String VERSION = Names.VERSION_FIELD;

    String OBJECT_ID_PATH = "/id/{" + ID + "}";
    String VERSION_PATH   = "/{" + VERSION + "}";

    String SHARE       = "/share";
    String OBJECT      = "/object";
    String REVOKE      = "/revoke";
    String KEYS        = "/keys";
    String OBJECT_KEYS = "/objectKeys";

    @GET( SHARE + OBJECT )
    Set<Share> getIncomingShares();

    @POST( SHARE + OBJECT + "/{" + ID + "}" )
    Response removeIncomingShares( @Path( ID ) VersionedObjectKey uuid);

    @POST( SHARE + OBJECT + SHARE )
    Response share( @Body SharingRequest request );

    @POST( SHARE + OBJECT + REVOKE )
    Response revokeAccess( @Body RevocationRequest request );

    // TODO: Consider creating objects here.
    @PUT( SHARE + KEYS )
    Set<UUID> addSearchPairs( @Body Map<VersionedObjectKey, ObjectSearchPair> indexPairs );

    @DELETE( SHARE + KEYS )
    Response removeKeys( @Body Set<VersionedObjectKey> uuids );

    @GET( SHARE + OBJECT + OBJECT_ID_PATH + VERSION_PATH )
    byte[] getSearchPair( @Path( ID ) UUID id, @Path( VERSION ) long version);

}
