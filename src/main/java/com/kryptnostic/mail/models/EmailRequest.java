package com.kryptnostic.mail.models;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.kryptnostic.kodex.v1.constants.Names;

public class EmailRequest {
    private final Optional<String>   from;
    private final String[]           to;
    private final Optional<String[]> cc;
    private final Optional<String[]> bcc;

    @JsonCreator
    public EmailRequest(
            @JsonProperty( Names.FROM_FIELD ) Optional<String> from,
            @JsonProperty( Names.TO_FIELD ) String[] to,
            @JsonProperty( Names.CC_FIELD ) Optional<String[]> cc,
            @JsonProperty( Names.BCC_FIELD ) Optional<String[]> bcc ) {

        this.from = Preconditions.checkNotNull( from );
        this.to = ImmutableList.copyOf( Iterables.filter( Arrays.asList( Preconditions.checkNotNull( to ) ),
                new Predicate<String>() {

                    @Override
                    public boolean apply( String input ) {
                        return StringUtils.isNotBlank( input );
                    }

                } ) ).toArray( new String[ 0 ] );
        this.cc = Preconditions.checkNotNull( cc );
        this.bcc = Preconditions.checkNotNull( bcc );
        Preconditions.checkState( this.to.length > 0 );
    }

    @JsonProperty( Names.FROM_FIELD )
    public Optional<String> getFrom() {
        return from;
    }

    @JsonProperty( Names.TO_FIELD )
    public String[] getTo() {
        return to;
    }

    @JsonProperty( Names.CC_FIELD )
    public Optional<String[]> getCc() {
        return cc;
    }

    @JsonProperty( Names.BCC_FIELD )
    public Optional<String[]> getBcc() {
        return bcc;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( bcc == null ) ? 0 : bcc.hashCode() );
        result = prime * result + ( ( cc == null ) ? 0 : cc.hashCode() );
        result = prime * result + ( ( from == null ) ? 0 : from.hashCode() );
        result = prime * result + Arrays.hashCode( to );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        EmailRequest other = (EmailRequest) obj;
        if ( bcc == null ) {
            if ( other.bcc != null ) return false;
        } else if ( !bcc.equals( other.bcc ) ) return false;
        if ( cc == null ) {
            if ( other.cc != null ) return false;
        } else if ( !cc.equals( other.cc ) ) return false;
        if ( from == null ) {
            if ( other.from != null ) return false;
        } else if ( !from.equals( other.from ) ) return false;
        if ( !Arrays.equals( to, other.to ) ) return false;
        return true;
    }

}
