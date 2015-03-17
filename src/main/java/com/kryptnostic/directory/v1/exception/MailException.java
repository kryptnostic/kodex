package com.kryptnostic.directory.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST )
public class MailException extends RuntimeException {
    private static final long serialVersionUID = -7318314712408566754L;
}
