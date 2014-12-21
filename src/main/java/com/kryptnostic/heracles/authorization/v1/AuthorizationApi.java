package com.kryptnostic.heracles.authorization.v1;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public interface AuthorizationApi {
    String CONTROLLER = "/authorization";
    String ACL = CONTROLLER + "/acl";
    String ACE = CONTROLLER + "/ace";
    String ID = "id";
    
    String ACE_BY_ID = ACE + "/{" + ID + "}";
    String ACL_BY_ID = ACE + "/{" + ID + "}";

    @GET( ACL_BY_ID )
    Acl getAcl(@Path(ID) int id );
    
    @POST(ACE)
    Ace createAce( @Body AceCreationRequest request );
    
    @POST(ACL)
    int createAcl( @Body List<Integer> aces );
    
    @DELETE( ACE_BY_ID )
    void deleteAce( @Path(ID) int id );

    @DELETE( ACL_BY_ID )
    void deleteAcl( @Path(ID) int id );
}
