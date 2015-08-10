package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.exception.MailException;
import com.kryptnostic.directory.v1.exception.RealmMismatchException;
import com.kryptnostic.directory.v1.exception.ReservationTakenException;
import com.kryptnostic.directory.v1.exception.UserUpdateException;
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;

/**
 * RESTful API for adding, modifying, and removing users on the Kryptnostic platform.
 *
 * @author Nick Hewitt
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface UserDirectoryApi {
    public static final String CONTROLLER          = "/directory";
    public static final String ID                  = "id";
    public static final String EMAIL               = "email";
    public static final String REALM               = "realm";
    public static final String USERNAME            = "username";
    public static final String USERS               = "/users";
    public static final String RESET               = "/reset";
    public static final String ID_PATH             = "/{" + ID + "}";
    public static final String ID_WITH_DOT         = "/{" + ID + ":.+}";
    public static final String REALM_PATH          = "/{" + REALM + "}";
    public static final String USERNAME_PATH       = "/{" + USERNAME + "}";
    public static final String EMAIL_PATH          = "/" + EMAIL + "/{" + EMAIL + "}";   // +EMAIL needed to
                                                                                          // disambiguate from get user
    public static final String EMAIL_PATH_WITH_DOT = "/" + EMAIL + "/{" + EMAIL + ":.+}";

    /**
     * Get the account details for a given user.
     *
     * @param userId String form of reserved {@link UUID}
     * @return {@link UserResponse}
     */
    @GET( USERS + ID_PATH )
    Optional<User> getUser( @Path( ID ) UUID userId ); // developer

    /**
     * Delete a specific user, removing it from the directory.
     *
     * @param userId the user id of the user to be deleted.
     * @return the UUID of the user deleted if any.
     * @throws RealmMismatchException
     */
    @DELETE( USERS + ID_PATH )
    Optional<UUID> deleteUser( @Path( ID ) UUID userId ) throws RealmMismatchException; // developer

    /**
     * Allows resolving an e-mail to a UUID. This is an open API.
     *
     * @param email
     * @return
     */
    @GET( USERS + EMAIL_PATH )
    Optional<UUID> resolve( @Path( EMAIL ) String email );

    /**
     * Deprecated API. Will be removed in next version.
     *
     * @param realm The realm in which to look for the user.
     * @param username The username to perform map to a UUID.
     * @return The UUID for the user.
     */
    @Deprecated
    @GET( USERS + REALM_PATH + USERNAME_PATH )
    Optional<UUID> resolve( @Path( REALM ) String realm, @Path( USERNAME ) String username );

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
    @PUT( USERS + ID_PATH )
    Optional<UUID> resetPassword( @Path( ID ) UUID userKey, @Body String newPassword ) throws UserUpdateException,
            ReservationTakenException, BadRequestException, MailException;

}
