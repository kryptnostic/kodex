package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.linear.EnhancedBitMatrix.SingularMatrixException;
import com.kryptnostic.search.v1.models.request.SearchRequest;
import com.kryptnostic.utils.SerializationTestUtils;

public class SearchRequestTests extends SerializationTestUtils {

    @Test
    public void serializeTest() throws JsonParseException, JsonMappingException, IOException, SingularMatrixException {
        byte[] searchToken = new byte[ 64 ];
        new Random().nextBytes( searchToken );
        SearchRequest req1 = SearchRequest.searchToken( searchToken );
        SearchRequest req2 = new SearchRequest(
                Arrays.asList( searchToken ),
                30,
                31 );

        SearchRequest out1 = deserialize( serialize( req1 ), SearchRequest.class );
        SearchRequest out2 = deserialize( serialize( req2 ), SearchRequest.class );

        Assert.assertEquals( req1.getSearchToken(), out1.getSearchToken() );
        Assert.assertEquals( req1.getMaxResults(), out1.getMaxResults() );
        Assert.assertEquals( req1.getOffset(), out1.getOffset() );
        Assert.assertEquals( req1, out1 );

        Assert.assertEquals( req2.getSearchToken(), out2.getSearchToken() );
        Assert.assertEquals( req2.getMaxResults(), out2.getMaxResults() );
        Assert.assertEquals( req2.getOffset(), out2.getOffset() );
        Assert.assertEquals( req2, out2 );
    }
}
