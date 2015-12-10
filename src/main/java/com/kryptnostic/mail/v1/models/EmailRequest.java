package com.kryptnostic.mail.v1.models;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.constants.Names;

public class EmailRequest implements Serializable {
    private static final long        serialVersionUID = -1654230107574687685L;

    private final Optional<String>   from;
    private final String[]           to;
    private final Optional<String[]> cc;
    private final Optional<String[]> bcc;

    private final Optional<String>   subject;
    private final Optional<String>   body;

    @JsonCreator
    @JsonIgnoreProperties(
        ignoreUnknown = true )
    public EmailRequest(
            @JsonProperty( Names.FROM_FIELD ) Optional<String> from,
            @JsonProperty( Names.TO_FIELD ) String[] to,
            @JsonProperty( Names.CC_FIELD ) Optional<String[]> cc,
            @JsonProperty( Names.BCC_FIELD ) Optional<String[]> bcc,
            @JsonProperty( Names.SUBJECT_FIELD ) Optional<String> subject,
            @JsonProperty( Names.BODY_FIELD ) Optional<String> body) {
        Preconditions.checkArgument( Preconditions.checkNotNull( to ).length > 0 );
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
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

    @JsonProperty( Names.SUBJECT_FIELD )
    public Optional<String> getSubject() {
        return subject;
    }

    @JsonProperty( Names.BODY_FIELD )
    public Optional<String> getBody() {
        return body;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( bcc == null ) ? 0 : bcc.hashCode() );
        result = prime * result + ( ( body == null ) ? 0 : body.hashCode() );
        result = prime * result + ( ( cc == null ) ? 0 : cc.hashCode() );
        result = prime * result + ( ( from == null ) ? 0 : from.hashCode() );
        result = prime * result + ( ( subject == null ) ? 0 : subject.hashCode() );
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
        if ( body == null ) {
            if ( other.body != null ) return false;
        } else if ( !body.equals( other.body ) ) return false;
        if ( cc == null ) {
            if ( other.cc != null ) return false;
        } else if ( !cc.equals( other.cc ) ) return false;
        if ( from == null ) {
            if ( other.from != null ) return false;
        } else if ( !from.equals( other.from ) ) return false;
        if ( subject == null ) {
            if ( other.subject != null ) return false;
        } else if ( !subject.equals( other.subject ) ) return false;
        if ( !Arrays.equals( to, other.to ) ) return false;
        return true;
    }

}
