package com.kryptnostic.mail.v1.models;

import org.junit.Test;

import com.google.common.base.Optional;
import com.kryptnostic.mail.models.EmailRequest;
import com.kryptnostic.utils.BaseJacksonSerializationTest;

public class EmailRequestTest extends BaseJacksonSerializationTest<EmailRequest> {

    @Override
    protected EmailRequest getSampleData() {
        String toAddress = "kryptodoge@kryptnostic.com";

        return new EmailRequest(
                Optional.<String> absent(),
                new String[] { toAddress },
                Optional.<String[]> absent(),
                Optional.<String[]> absent() );
    }

    @Override
    protected Class<EmailRequest> getClazz() {
        return EmailRequest.class;
    }

    @Test(
        expected = IllegalStateException.class )
    public void testNoTo() {
        new EmailRequest(
                Optional.<String> absent(),
                new String[] {},
                Optional.<String[]> absent(),
                Optional.<String[]> absent() );
    }

    @Test(
        expected = NullPointerException.class )
    public void testNullTo() {
        new EmailRequest(
                Optional.<String> absent(),
                null,
                Optional.<String[]> absent(),
                Optional.<String[]> absent() );
    }

}
