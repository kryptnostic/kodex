package com.kryptnostic.directory.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST,
    reason = "Requested includes unauthorized group." )
public class UnauthorizedGroupRequestException extends RuntimeException {
    private static final long serialVersionUID = 1279438730669495588L;
}
