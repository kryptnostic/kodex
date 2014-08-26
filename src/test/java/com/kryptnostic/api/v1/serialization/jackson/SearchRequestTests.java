package com.kryptnostic.api.v1.serialization.jackson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.kryptnostic.api.v1.models.request.SearchRequest;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.linear.BitUtils;

public class SearchRequestTests {

    private static final int LEN = 256;
    private static ObjectMapper mapper;

    @BeforeClass
    public static void init() {
        mapper = new KodexObjectMapperFactory().getObjectMapper();
    }

    @Test
    public void serializeTest() throws JsonGenerationException, JsonMappingException, IOException {
        BitVector searchToken = BitUtils.randomVector(LEN);
        SearchRequest req = new SearchRequest(searchToken, Optional.absent(), Optional.absent(), Optional.absent(),
                Optional.absent());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, req);

        String str = "{\"query\":\"" + BitVectors.marshalBitvector(searchToken)
                + "\",\"maxResults\":30,\"offset\":0,\"sortDate\":\"DESC\",\"sortScore\":\"DESC\"}";

        Assert.assertEquals(str, out.toString());
    }

    @Test
    @Ignore
    public void deserializeTest() throws JsonParseException, JsonMappingException, IOException {
        BitVector searchToken = BitUtils.randomVector(LEN);
        SearchRequest req = new SearchRequest(searchToken, Optional.of(new Integer(30)), Optional.of(new Integer(0)),
                Optional.of(SearchRequest.SortDirection.DESC), Optional.of(SearchRequest.SortDirection.DESC));
        String str = "{\"query\":\"" + BitVectors.marshalBitvector(searchToken)
                + "\",\"maxResults\":30,\"offset\":0,\"sortDate\":\"DESC\",\"sortScore\":\"DESC\"}";

        SearchRequest out = mapper.readValue(str, SearchRequest.class);

        Assert.assertEquals(req, out);
    }
}
