package com.kryptnostic.kodex.v1.models.response;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Immutable basic response model for web services http://wiki.krypt.local/display/PS/Basic+Response+Model
 *
 * @author sina
 */
@Immutable
public class BasicResponse<T> {
    protected final T       data;
    protected final int     status;
    protected final boolean success;

    @JsonCreator
    public BasicResponse(
            @JsonProperty( Names.DATA_FIELD ) T data,
            @JsonProperty( Names.STATUS_FIELD ) int status,
            @JsonProperty( Names.SUCCESS_FIELD ) boolean success ) {
        this.data = data;
        this.status = status;
        this.success = success;
    }

    @JsonProperty( Names.DATA_FIELD )
    public T getData() {
        return data;
    }

    @JsonProperty( Names.STATUS_FIELD )
    public int getStatus() {
        return status;
    }

    @JsonProperty( Names.SUCCESS_FIELD )
    public boolean isSuccess() {
        return success;
    }

}
