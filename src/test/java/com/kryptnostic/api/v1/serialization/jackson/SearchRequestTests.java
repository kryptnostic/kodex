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
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.linear.BitUtils;
import com.kryptnostic.search.v1.models.request.SearchRequest;

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
        SearchRequest req = SearchRequest.searchToken(searchToken);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, req);

        String str = "{\"query\":\"" + BitVectors.marshalBitvector(searchToken)
                + "\",\"maxResults\":30,\"offset\":0,\"sortDate\":\"DESC\",\"sortScore\":\"DESC\"}";

        Assert.assertEquals(str, out.toString());
    }

    @Test
    public void deserializeTest() throws JsonParseException, JsonMappingException, IOException {
        BitVector searchToken = BitUtils.randomVector(LEN);
        SearchRequest req = SearchRequest.searchToken(searchToken);
        String str = "{\"query\":\"" + BitVectors.marshalBitvector(searchToken)
                + "\",\"maxResults\":30,\"offset\":0,\"sortDate\":\"DESC\",\"sortScore\":\"DESC\"}";

        SearchRequest out = mapper.readValue(str, SearchRequest.class);

        Assert.assertEquals(req.getSearchToken(), out.getSearchToken());
        Assert.assertEquals(req.getMaxResults(), out.getMaxResults());
        Assert.assertEquals(req.getOffset(), out.getOffset());
        Assert.assertEquals(req.getSortDate(), out.getSortDate());
        Assert.assertEquals(req.getSortScore(), out.getSortDate());
    }
}
