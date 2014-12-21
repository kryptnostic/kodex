package com.kryptnostic.heracles.principals.v1;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.heracles.principals.v1.requests.GroupCreationRequest;
import com.kryptnostic.heracles.principals.v1.requests.UserRegistrationRequest;
import com.kryptnostic.kodex.v1.constants.Names;

public interface DirectoryApi {
    public static final String CONTROLLER  = "/directory";

    public static final String USER        = "/user";
    public static final String GROUP       = "/group";

    public static final String USER_BY_ID  = USER + "/{" + Names.ID_FIELD + "}";
    public static final String GROUP_BY_ID = GROUP + "/{" + Names.ID_FIELD + "}";

    public static interface PARAM {
        String REALM_ID = "realm";
        String USER_ID  = "user";
        String GROUP_ID = "group";
    }

    @GET( USER )
    User getUser();

    @POST( USER )
    User createUser( @Path( PARAM.REALM_ID ) String realm, UserRegistrationRequest request );

    @POST( GROUP )
    Group createGroup( GroupCreationRequest request );

    @PUT( GROUP )
    void addUserToGroup( @Path( PARAM.USER_ID ) String user, @Path( PARAM.GROUP_ID ) String group );

    @DELETE( GROUP )
    void removeUserFromGroup( @Path( PARAM.USER_ID ) String user, @Path( PARAM.GROUP_ID ) String group );

    @DELETE( USER )
    void deleteUser( @Path( PARAM.USER_ID ) String user );

    @DELETE( GROUP )
    void deleteGroup( @Path( PARAM.GROUP_ID ) String group );
}
