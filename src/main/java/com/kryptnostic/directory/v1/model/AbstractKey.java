package com.kryptnostic.directory.v1.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.kryptnostic.directory.v1.principal.Principal;
import com.kryptnostic.kodex.v1.constants.Names;

public abstract class AbstractKey implements Principal, Serializable {
    private static final long       serialVersionUID    = 737151589325060845L;
    public static final String      DELIMITER           = "|";
    public static final String      VALID_REALM_REGEX   = "^[A-Za-z0-9\\.\\-_]+$";
    public static final int         MAX_USERNAME_LENGTH = 1024;
    public static final int         MAX_REALM_LENGTH    = 1024;

    protected static final Splitter splitter            = Splitter.on( DELIMITER ).limit( 2 ).trimResults()
                                                                .omitEmptyStrings();
    protected final String          name;
    protected final String          realm;

    @JsonCreator
    public AbstractKey( @JsonProperty( Names.REALM_FIELD ) String realm, @JsonProperty( Names.NAME_FIELD ) String name ) {
        this.realm = realm;
        this.name = name;
    }

    @JsonProperty( Names.NAME_FIELD )
    public String getName() {
        return name;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getRealm() {
        return realm;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
        return result;
    }

    @JsonIgnore
    public static boolean isValidUsername( String name ) {
        Preconditions.checkArgument( name.length() < MAX_USERNAME_LENGTH, "Username cannot be longer than "
                + MAX_USERNAME_LENGTH + "characters" );
        Preconditions.checkArgument( !name.contains( DELIMITER ) );
        return true;
    }

    @JsonIgnore
    public static boolean isValidRealm( String realm ) {
        Preconditions.checkArgument( realm.matches( VALID_REALM_REGEX ), "Realm must consist of valid characters: "
                + VALID_REALM_REGEX );
        Preconditions.checkArgument( realm.length() < MAX_REALM_LENGTH, "Realm name cannot be longer than "
                + MAX_REALM_LENGTH + "characters" );
        return true;
    }
}
