package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.kryptnostic.directory.v1.exception.ActiveReservationException;
import com.kryptnostic.directory.v1.exception.AddUserException;
import com.kryptnostic.directory.v1.exception.MailException;
import com.kryptnostic.directory.v1.exception.RealmMismatchException;
import com.kryptnostic.directory.v1.exception.ReservationTakenException;
import com.kryptnostic.directory.v1.exception.ReservationTokenMismatchException;
import com.kryptnostic.directory.v1.exception.UserUpdateException;
import com.kryptnostic.directory.v1.model.Reservation;
import com.kryptnostic.directory.v1.model.request.ActivateUserRequest;
import com.kryptnostic.directory.v1.model.request.ReserveUserRequest;
import com.kryptnostic.directory.v1.model.request.UpdateUserRequest;
import com.kryptnostic.directory.v1.model.response.ActivateUserResponse;
import com.kryptnostic.directory.v1.model.response.ReserveUserResponse;
import com.kryptnostic.directory.v1.model.response.UserResponse;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;

/**
 * RESTful API for adding, modifying, and removing users on the Kryptnostic platform.
 *
 * @author Nick Hewitt
 *
 */
public interface UserDirectoryApi {
    public static final String ID           = "id";
    public static final String CONTROLLER   = "/directory";
    public static final String USERS        = "/users";
    public static final String RESERVATIONS = "/reservations";
    public static final String RESET     = "/reset";
    public static final String ID_PATH      = "/{" + ID + "}";
    public static final String ID_WITH_DOT  = "/{" + ID + ":.+}";

    /**
     * Reserve an account for a user with specified Realm and Username.
     *
     * @param request {@link ReserveUserRequest}
     * @return {@link ReserveUserResponse}
     * @throws ReservationTakenException
     * @throws BadRequestException
     */
    @POST( RESERVATIONS )
    ReserveUserResponse reserve( @Body ReserveUserRequest request ) throws ReservationTakenException,
            BadRequestException; // developer

    /**
     * Get the reservation corresponding to the reservation ID.
     *
     * @param userId String form of reserved {@link UUID}: {@code [ realm ].[ username ] }
     * @return {@link Reservation}
     */
    @GET( RESERVATIONS + ID_PATH )
    Reservation getReservation( @Path( ID ) String userId ); // developer

    /**
     * Delete the reservation corresponding to the reservation ID. If the reservation is already active, it cannot be
     * deleted. You must first delete the user account, then may delete the reservation.
     *
     * @param userId String form of reserved {@link UUID}: {@code [ realm ].[ username ] }
     * @return {@link Reservation}
     * @throws BadRequestException
     * @throws RealmMismatchException
     */
    @DELETE( RESERVATIONS + ID_PATH )
    Reservation deleteReservation( @Path( ID ) String userId ) throws BadRequestException, RealmMismatchException; // developer

    /**
     * Activate a user account, using the one-time user reservation token to create an account with a password. This is
     * intended to be called from the client device so that developer and user creds can be kept on separate devices.
     *
     * @param request {@link ActivateUserRequest}
     * @return {@link ActivateUserResponse}
     * @throws AddUserException
     * @throws ReservationTokenMismatchException
     * @throws ActiveReservationException
     * @throws ResourceNotFoundException
     */
    @POST( USERS )
    ActivateUserResponse activate( @Body ActivateUserRequest request ) throws AddUserException,
            ResourceNotFoundException, ActiveReservationException, ReservationTokenMismatchException; // public

    /**
     * Update and existing user account details, including username and password. If the username is changed, then that
     * username will become available for other users in the realm to reserve.
     *
     * @param userId String form of reserved {@link UUID}: {@code [ realm ].[ username ] }
     * @param request {@link UpdateUserRequest}
     * @return {@link UserResponse}
     * @throws UserUpdateException
     * @throws ReservationTakenException
     * @throws BadRequestException
     * @throws MailException
     */
    @POST( USERS + ID_PATH )
    UserResponse update( @Path( ID ) String userId, @Body UpdateUserRequest request ) throws UserUpdateException,
            ReservationTakenException, BadRequestException, MailException; // user

    /**
     * Get the account details for a given user.
     *
     * @param userId String form of reserved {@link UUID}: {@code [ realm ].[ username ] }
     * @return {@link UserResponse}
     */
    @GET( USERS + ID_PATH )
    UserResponse getUser( @Path( ID ) String userId ); // developer

    /**
     * Delete a specific user, removing it from the active directory.
     *
     * @param userId String form of reserved {@link UUID}: {@code [ realm ].[ username ] }
     * @return {@link UserResponse}
     * @throws RealmMismatchException
     */
    @DELETE( USERS + ID_PATH )
    UserResponse deleteUser( @Path( ID ) String userId ) throws RealmMismatchException; // developer

    /**
     * Reset a users password given a reset token
     * @param userId
     * @param request
     * @return
     * @throws UserUpdateException
     * @throws ReservationTakenException
     * @throws BadRequestException
     * @throws MailException
     */
    @POST( USERS + RESET + ID_PATH )
    UserResponse resetPassword( @Path( ID ) String userId, @Body UpdateUserRequest request) throws UserUpdateException, ReservationTakenException,
            BadRequestException, MailException;
}
