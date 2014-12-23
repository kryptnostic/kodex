package com.kryptnostic.kodex.v1.models.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Immutable basic response model for web services http://wiki.krypt.local/display/PS/Basic+Response+Model
 * 
 * @author sina
 */
public class BasicResponse<T> {
    protected static final String DATA    = "data";
    protected static final String STATUS  = "status";
    protected static final String SUCCESS = "success";

    protected final T             data;
    protected final int           status;
    protected final boolean       success;

    @JsonCreator
    public BasicResponse(
            @JsonProperty( DATA ) T data,
            @JsonProperty( STATUS ) int status,
            @JsonProperty( SUCCESS ) boolean success ) {
        this.data = data;
        this.status = status;
        this.success = success;
    }

    @JsonProperty( DATA )
    public T getData() {
        return data;
    }

    @JsonProperty( STATUS )
    public int getStatus() {
        return status;
    }

    @JsonProperty( SUCCESS )
    public boolean isSuccess() {
        return success;
    }

}
