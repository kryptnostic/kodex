package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.SerializationTestUtils;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.search.v1.models.request.SearchRequest;

public class SearchRequestTests extends SerializationTestUtils {

    private static final int LEN = 256;

    @Test
    public void serializeTest() throws JsonGenerationException, JsonMappingException, IOException {
        BitVector searchToken = BitVectors.randomVector(LEN);
        SearchRequest req = SearchRequest.searchToken(searchToken);

        String str = "{\"query\":[\"" + BitVectors.marshalBitvector(searchToken)
                + "\"],\"maxResults\":30,\"offset\":0,\"sortDate\":\"DESC\",\"sortScore\":\"DESC\"}";

        Assert.assertEquals(str, serialize(req));
    }

    @Test
    public void deserializeTest() throws JsonParseException, JsonMappingException, IOException {
        BitVector searchToken = BitVectors.randomVector(LEN);
        SearchRequest req = SearchRequest.searchToken(searchToken);
        String str = "{\"query\":[\"" + BitVectors.marshalBitvector(searchToken)
                + "\"],\"maxResults\":30,\"offset\":0,\"sortDate\":\"DESC\",\"sortScore\":\"DESC\"}";

        SearchRequest out = deserialize(str, SearchRequest.class);

        Assert.assertEquals(req.getSearchToken(), out.getSearchToken());
        Assert.assertEquals(req.getMaxResults(), out.getMaxResults());
        Assert.assertEquals(req.getOffset(), out.getOffset());
        Assert.assertEquals(req.getSortDate(), out.getSortDate());
        Assert.assertEquals(req.getSortScore(), out.getSortDate());
    }
}
