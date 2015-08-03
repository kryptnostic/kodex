package com.kryptnostic.directory.v1.model;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.directory.v1.exception.ActiveReservationException;
import com.kryptnostic.directory.v1.exception.ReservationTokenMismatchException;
import com.kryptnostic.directory.v1.model.request.ReserveUserRequest;
import com.kryptnostic.kodex.v1.constants.Names;

public final class Reservation extends ReserveUserRequest {
    private final UserStatus       status;
    private final DateTime         created;
    private final ReservationToken token;

    private Reservation( ReservationBuilder builder ) {
        super( builder.realm, builder.username, builder.email, builder.givenName );
        this.status = builder.status;
        this.created = builder.created;
        this.token = builder.token;
    }

    @JsonCreator
    public Reservation(
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.USERNAME_FIELD ) String username,
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.GIVEN_NAME_FIELD ) String givenName,
            @JsonProperty( Names.STATUS_FIELD ) UserStatus status,
            @JsonProperty( Names.TIME_FIELD ) DateTime created,
            @JsonProperty( Names.TOKEN_PROPERTY ) ReservationToken token ) {
        super( realm, username, email, givenName );
        this.status = status;
        this.created = created;
        this.token = token;
    }

    public static Reservation reserve( ReserveUserRequest request ) {
        return new Reservation.ReservationBuilder( request.getRealm(), request.getUsername() )
                .withGivenName( request.getGivenName() ).withEmail( request.getEmail() )
                .withStatus( UserStatus.RESERVED ).createdNow().withNewToken().build();
    }

    public Reservation activate( ReservationToken token ) throws ReservationTokenMismatchException,
            ActiveReservationException {
        if ( this.status != UserStatus.RESERVED ) {
            throw new ActiveReservationException();
        }
        if ( !this.token.equals( token ) ) {
            throw new ReservationTokenMismatchException();
        }
        return new ReservationBuilder( this.getRealm(), this.getUsername() ).withGivenName( this.getGivenName() )
                .withEmail( this.getEmail() ).withStatus( UserStatus.ACTIVE ).withToken( this.getToken() )
                .createdAt( this.created ).build();
    }

    @JsonProperty( Names.STATUS_FIELD )
    public UserStatus getStatus() {
        return status;
    }

    @JsonProperty( Names.TOKEN_PROPERTY )
    public ReservationToken getToken() {
        return token;
    }

    @JsonProperty( Names.TIME_FIELD )
    public DateTime getTimeCreated() {
        return created;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( created == null ) ? 0 : created.hashCode() );
        result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
        result = prime * result + ( ( token == null ) ? 0 : token.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( !super.equals( obj ) ) return false;
        if ( getClass() != obj.getClass() ) return false;
        Reservation other = (Reservation) obj;
        if ( created == null ) {
            if ( other.created != null ) return false;
        } else if ( !created.equals( other.created ) ) return false;
        if ( status != other.status ) return false;
        if ( token == null ) {
            if ( other.token != null ) return false;
        } else if ( !token.equals( other.token ) ) return false;
        return true;
    }

    public static class ReservationBuilder {
        private String           realm;
        private String           username;
        private String           email;
        private String           givenName;
        private UserStatus       status;
        private ReservationToken token;
        private DateTime         created;

        public ReservationBuilder( String realm, String username ) {
            this.realm = realm;
            this.username = username;
        }

        public ReservationBuilder withToken( ReservationToken token ) {
            this.token = token;
            return this;
        }

        public ReservationBuilder withNewToken() {
            this.token = ReservationToken.getSecureToken();
            return this;
        }

        public ReservationBuilder createdAt( DateTime time ) {
            this.created = time;
            return this;
        }

        public ReservationBuilder createdNow() {
            this.created = DateTime.now();
            return this;
        }

        public ReservationBuilder withGivenName( String givenName ) {
            this.givenName = givenName;
            return this;
        }

        public ReservationBuilder withStatus( UserStatus status ) {
            this.status = status;
            return this;
        }

        public ReservationBuilder withEmail( String email ) {
            this.email = email;
            return this;
        }

        public Reservation build() {
            Preconditions.checkNotNull( this.realm );
            Preconditions.checkNotNull( this.username );
            Preconditions.checkNotNull( this.email );
            Preconditions.checkNotNull( this.givenName );
            Preconditions.checkNotNull( this.status );
            Preconditions.checkNotNull( this.token );
            Preconditions.checkNotNull( this.created );
            return new Reservation( this );
        }
    }
}
