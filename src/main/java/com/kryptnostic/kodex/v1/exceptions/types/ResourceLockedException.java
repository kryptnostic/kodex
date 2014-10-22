package com.kryptnostic.kodex.v1.exceptions.types;

import retrofit.RetrofitError;

@SuppressWarnings("serial")
public class ResourceLockedException extends Exception {

    public ResourceLockedException() {

    }

    public ResourceLockedException(String msg) {
        super(msg);
    }

    public ResourceLockedException(RetrofitError cause) {
        super(cause);
    }

}
