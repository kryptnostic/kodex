package com.kryptnostic.directory.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST,
    reason = "Organization realm and request user realm do not match." )
public class RealmMismatchException extends RuntimeException {
    private static final long serialVersionUID = 1544107975733654869L;
}
