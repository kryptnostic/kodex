package com.kryptnostic.v2.events.api;

import static com.kryptnostic.v2.constants.Names.ID_FIELD;
import static com.kryptnostic.v2.constants.Names.TYPE_FIELD;
import static com.kryptnostic.v2.constants.Names.VERSION_FIELD;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.v2.events.models.CreateSubscriptionsRequest;
import com.kryptnostic.v2.events.models.EventQuery;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SubscriptionApi {
    String CONTROLLER = "/subscriptions";

    Response subscribe( @Body CreateSubscriptionsRequest request );

    @GET( CONTROLLER )
    Set<VersionedObjectKey> getAllUnseenEvents();

    @GET( CONTROLLER + "/source/type/{" + TYPE_FIELD + "}" )
    Set<VersionedObjectKey> getUnseenEventsBySourceType();

    @GET( CONTROLLER + "/source/id/{" + ID_FIELD + "}/{" + VERSION_FIELD + "}" )
    Set<VersionedObjectKey> getUnseenEventsBySource(
            @Path( ID_FIELD ) UUID objectId,
            @Path( VERSION_FIELD ) long version);

    @GET( CONTROLLER + "/source/type/{" + TYPE_FIELD + "}/{" + VERSION_FIELD + "}" )
    Set<VersionedObjectKey> getUnseenEventsBySourceType( String type );

    @GET( CONTROLLER + "/event/type/{" + ID_FIELD + "}/{" + VERSION_FIELD + "}" )
    Set<VersionedObjectKey> getUnseenEventsByEventType();

    @POST( CONTROLLER )
    Set<VersionedObjectKey> filterUnseenEvents( EventQuery filter );

    @DELETE( CONTROLLER + "/source/id/{" + ID_FIELD + "}/{" + VERSION_FIELD + "}" )
    Set<VersionedObjectKey> clearUnseenEvents(
            @Path( ID_FIELD ) UUID objectId,
            @Path( VERSION_FIELD ) long version);

    // TODO: Add APIs for deleting subscriptions.

}
