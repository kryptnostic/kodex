package com.kryptnostic.kodex.v1.exceptions;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.UnauthorizedException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

public class DefaultErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError( RetrofitError cause ) {
        Response r = cause.getResponse();
        if ( r != null && r.getStatus() == 401 ) {
            return new UnauthorizedException( cause );
        }
        if ( r != null && r.getStatus() == 404 ) {
            return new ResourceNotFoundException( cause );
        }
        if ( r != null && r.getStatus() == 400 ) {
            String msg = parseMsg( cause );
            if ( msg != null ) {
                return new BadRequestException( msg );
            }
            return new BadRequestException( cause );
        }
        if ( r != null && r.getStatus() == 500 ) {
            String msg = parseMsg( cause );
            if ( msg != null ) {
                return RetrofitError.unexpectedError( cause.getUrl(), new KodexException( msg ) );
            }
        }

        return cause;
    }

    private String parseMsg( RetrofitError cause ) {
        Object raw;
        try {
            raw = cause.getBodyAs( TypeFactory.defaultInstance().constructType(
                    new TypeReference<BasicResponse<String>>() {} ) );
        } catch ( RuntimeException e ) {
            return null;
        }
        if ( raw instanceof BasicResponse ) {
            BasicResponse<String> msg = (BasicResponse<String>) raw;
            return msg.getData();
        }
        return null;
    }
}
