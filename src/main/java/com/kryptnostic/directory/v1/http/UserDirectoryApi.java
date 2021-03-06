package com.kryptnostic.directory.v1.http;

import java.util.Set;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.ContactDiscoveryRequest;
import com.kryptnostic.directory.v1.ContactDiscoveryResponse;
import com.kryptnostic.directory.v1.exception.MailException;
import com.kryptnostic.directory.v1.exception.RealmMismatchException;
import com.kryptnostic.directory.v1.exception.ReservationTakenException;
import com.kryptnostic.directory.v1.exception.UserUpdateException;
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ForbiddenException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * RESTful API for adding, modifying, and removing users on the Kryptnostic platform.
 *
 * @author Nick Hewitt
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface UserDirectoryApi {
    public static final String CONTROLLER             = "/directory";

    public static final String ID                     = "id";
    public static final String EMAIL                  = "email";
    public static final String ROLE                   = "role";
    public static final String REALM                  = "realm";
    public static final String SETTINGS_VAR           = "setting";

    public static final String DISCOVERY              = "/discovery";
    public static final String USER                   = "/user";
    public static final String USERS                  = "/users";
    public static final String RESET                  = "/reset";
    public static final String SET_LOGIN              = "/setlogin";
    public static final String INITIALIZED            = "/initialized";
    public static final String VALIDATE               = "/validate";
    public static final String SHARING                = "/sharing";
    public static final String SETTING                = "/setting";

    public static final String ID_PATH                = "/{" + ID + "}";
    public static final String ID_WITH_DOT            = "/{" + ID + ":.+}";
    public static final String EMAIL_PATH             = "/" + EMAIL + "/{" + EMAIL + "}";   // +EMAIL needed to
                                                                                            // disambiguate from get
                                                                                            // user
    public static final String REALM_PATH             = "/{" + REALM + "}";
    public static final String REALM_PATH_WITH_DOT    = "/{" + REALM + ":.+}";

    public static final String EMAIL_PATH_WITH_DOT    = "/" + EMAIL + "/{" + EMAIL + ":.+}";

    public static final String USER_ID_PATH           = USER + ID_PATH;
    public static final String SETTING_PATH           = SETTING + "/{" + SETTINGS_VAR + "}";
    public static final String ROLE_PATH              = "/" + ROLE;

    public static final String DELETE_USER_PATH       = USER + ID_PATH;
    public static final String SET_ROLE_FOR_USER_PATH = USER_ID_PATH + ROLE_PATH;
    public static final String FULL_USER_SETTINGS_PATH= USER + SETTING_PATH;

    /**
     * Get the account details for a given user.
     *
     * @param userId String form of reserved {@link UUID}
     * @return The user
     */
    @GET( CONTROLLER + USER + ID_PATH )
    Optional<User> getUser( @Path( ID ) UUID userId ); // developer

    /**
     * Get the account details for a set of users.
     *
     * @param userIds Set of UUIDs
     * @return The users
     */
    @POST( CONTROLLER + USERS )
    Set<User> getUsers( @Body Set<UUID> userIds ); // developer

    /**
     * Delete a specific user, removing it from the directory.
     *
     * @param userId the user id of the user to be deleted.
     * @return the UUID of the user deleted if any.
     * @throws RealmMismatchException
     */
    @DELETE( CONTROLLER + DELETE_USER_PATH )
    Optional<UUID> deleteUser( @Path( ID ) UUID userId ) throws RealmMismatchException; // developer

    /**
     * Allows resolving an e-mail to a UUID. This is an open API.
     *
     * @param email
     * @return
     */
    @GET( CONTROLLER + USER + EMAIL_PATH )
    Optional<UUID> resolve( @Path( EMAIL ) String email );

    /**
     * This API resets the users authenticator. It does not impact key information.
     *
     * @param userKey
     * @param newPassword
     * @return
     * @throws UserUpdateException
     * @throws ReservationTakenException
     * @throws BadRequestException
     * @throws MailException
     */
    @PUT( CONTROLLER + USER + ID_PATH )
    Optional<UUID> resetPassword( @Path( ID ) UUID userKey, @Body String newPassword ) throws UserUpdateException,
            ReservationTakenException, BadRequestException, MailException;

    /**
     * Allows discovering contacts by e-mail or name search.
     *
     * @param request E-mail and name search terms.
     * @return Scored soundex, metaphone, and exact match search results on name and e-mail as specified in the request.
     */
    @POST( CONTROLLER + DISCOVERY )
    ContactDiscoveryResponse discover( ContactDiscoveryRequest request );

    /**
     * Allows setting a role for a user. The caller must be a developer or super admin
     *
     * @param userId
     * @return
     */
    @POST( CONTROLLER + SET_ROLE_FOR_USER_PATH )
    UUID addRoleForUser( @Path( ID ) UUID userId, @Body String role );

    @POST( CONTROLLER + SET_LOGIN )
    Response setSuccessfulFirstLogin();

    @GET( CONTROLLER + REALM_PATH )
    Iterable<UUID> listUserInRealm( @Path( REALM ) String realm ) throws BadRequestException;

    @GET( CONTROLLER + INITIALIZED + REALM_PATH )
    Iterable<UUID> listInitializedUserInRealm( @Path( REALM ) String realm ) throws BadRequestException;

    @GET( CONTROLLER + USERS + INITIALIZED )
    Iterable<UUID> listInitializedUsersForAllRealms();

    @GET( CONTROLLER + USERS )
    Iterable<UUID> listUsersForAllRealms();
    
    @GET( CONTROLLER + VALIDATE + SHARING + EMAIL_PATH )
    UUID getUserIdForSharing( @Path( EMAIL ) String email) throws ResourceNotFoundException, ForbiddenException;
    
    @GET( CONTROLLER + FULL_USER_SETTINGS_PATH )
    Set<String> getUserSetting( @Path( SETTINGS_VAR ) String userSetting );
    
    @PUT( CONTROLLER + FULL_USER_SETTINGS_PATH )
    Response addToUserSetting( @Path( SETTINGS_VAR ) String userSetting, @Body Set<String> items );
    
    @DELETE( CONTROLLER + FULL_USER_SETTINGS_PATH )
    Response removeFromUserSetting( @Path( SETTINGS_VAR ) String userSetting, @Body Set<String> items );

}
