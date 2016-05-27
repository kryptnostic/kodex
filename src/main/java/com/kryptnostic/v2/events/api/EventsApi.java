package com.kryptnostic.v2.events.api;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.v2.constants.Names;

import retrofit.http.Body;
import retrofit.http.POST;

public interface EventsApi {
    String CONTROLLER     = "/events";
    String TYPE_ID   = "/" + Names.ID_FIELD;
    String TYPE_NAME = "/" + Names.TYPE_FIELD;

    @POST( TYPE_ID )
    void registerEventTypes( @Body Set<UUID> typeId );

    @POST( TYPE_NAME )
    void registerEventTypes( @Body String type );
    
}
