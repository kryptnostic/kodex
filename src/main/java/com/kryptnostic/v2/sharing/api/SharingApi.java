package com.kryptnostic.v2.sharing.api;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.sharing.models.RevocationRequest;
import com.kryptnostic.v2.sharing.models.Share;
import com.kryptnostic.v2.sharing.models.SharingRequest;
import com.kryptnostic.v2.sharing.models.VersionedObjectSearchPair;
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
    Iterable<Share> getIncomingShares();

    @DELETE( SHARE + OBJECT + "/{" + ID + "}/{" + VERSION + "}" )
    Response removeIncomingShare( @Path( ID ) UUID objectKey, @Path( VERSION ) long version);

    @POST( SHARE + OBJECT + SHARE )
    Response share( @Body SharingRequest request );

    @POST( SHARE + OBJECT + REVOKE )
    Response revokeAccess( @Body RevocationRequest request );

    // TODO: Consider creating objects here.
    @POST( SHARE + KEYS )
    Response addSearchPairs( @Body Set<VersionedObjectSearchPair> versionedObjectSearchPairs );

    // TODO: Once versions exceed values supported by JS ~2^54 we will have problems if ecmascript hasn't caught up by
    @PUT( SHARE + KEYS + OBJECT_ID_PATH + VERSION_PATH )
    Response addSearchPair( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] objectSearchPair);

    @GET( SHARE + OBJECT + OBJECT_ID_PATH + VERSION_PATH )
    byte[] getSearchPair( @Path( ID ) UUID id, @Path( VERSION ) long version);

    @DELETE( SHARE + KEYS )
    Response removeSearchPairs( @Body Set<VersionedObjectKey> uuids );
}
