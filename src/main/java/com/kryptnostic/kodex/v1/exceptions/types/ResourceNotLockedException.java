package com.kryptnostic.kodex.v1.exceptions.types;

import retrofit.RetrofitError;

@SuppressWarnings("serial")
public class ResourceNotLockedException extends Exception {

    public ResourceNotLockedException() {

    }

    public ResourceNotLockedException(String msg) {
        super(msg);
    }

    public ResourceNotLockedException(RetrofitError cause) {
        super(cause);
    }

}
