package com.kryptnostic.heracles.registration.v1;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.kryptnostic.heracles.principals.v1.requests.UserRegistrationRequest;
import com.kryptnostic.heracles.registration.v1.requests.DeveloperRegistrationRequest;
import com.kryptnostic.heracles.registration.v1.requests.RequestStatus;
import com.kryptnostic.heracles.registration.v1.requests.RequestUpdateRequest;
import com.kryptnostic.heracles.registration.v1.responses.RegistrationResponse;
import com.kryptnostic.heracles.registration.v1.responses.RequestUpdateResponse;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Registration API for users and developers. 
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface RegistrationApi {
    String CONTROLLER = "/registration";
    String DEVELOPER = "/developer";
    String USER = "/user";
    
    String OPEN = "/open";
    String USER_STATUS = USER+"/status";
    String DEVELOPER_STATUS = DEVELOPER+"/status";
    
    public final class Param {
        private Param() {}
        public static final String ID = "/{" + Names.ID_FIELD + "}";
    }
    
    @GET(DEVELOPER)
    Map<Integer, Collection<DeveloperRegistrationRequest>> getDeveloperRegistrations();
    
    @GET(USER)
    Map<Integer, Collection<UserRegistrationRequest>> getUserRegistrations();
    
    @POST(DEVELOPER)
    RegistrationResponse register( @Body DeveloperRegistrationRequest request );
    
    @POST(USER)
    RegistrationResponse register( @Body UserRegistrationRequest request );
   
    @POST(DEVELOPER_STATUS)
    Collection<DeveloperRegistrationRequest> getDeveloperRegistrations( @Body Set<RequestStatus> filter );
    
    @POST(USER_STATUS)
    Collection<UserRegistrationRequest> getUserRegistrations(@Body Set<RequestStatus> filter);
    
    @POST(DEVELOPER + Param.ID )
    RequestUpdateResponse updateDeveloperRequest( @Path(Names.ID_FIELD) String developerRequestId , @Body RequestUpdateRequest request );
    
    @POST(USER + Param.ID)
    RequestUpdateResponse updateUserRequest( @Path(Names.ID_FIELD) String userRequestId , @Body RequestUpdateRequest request);
}
