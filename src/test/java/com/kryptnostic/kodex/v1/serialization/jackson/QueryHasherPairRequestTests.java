package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.crypto.EncryptedSearchPrivateKey;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.linear.EnhancedBitMatrix.SingularMatrixException;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.multivariate.util.SimplePolynomialFunctions;
import com.kryptnostic.storage.v1.models.request.QueryHasherPairRequest;

public class QueryHasherPairRequestTests extends BaseSerializationTest {

    @Test
    public void deserializeTest() throws IOException, SingularMatrixException {
        SimplePolynomialFunction globalHash = SimplePolynomialFunctions.denseRandomMultivariateQuadratic( 128, 64 );
        PrivateKey fhePrivateKey = new PrivateKey( 128, 64 );
        EncryptedSearchPrivateKey esp = new EncryptedSearchPrivateKey( (int) Math.sqrt( globalHash.getOutputLength() ) );
        Pair<SimplePolynomialFunction, SimplePolynomialFunction> p = esp.getQueryHasherPair( globalHash, fhePrivateKey );
        QueryHasherPairRequest qhp = new QueryHasherPairRequest( p.getLeft(), p.getRight() );
        String serialized = mapper.writeValueAsString( qhp );
        QueryHasherPairRequest recovered = mapper.readValue( serialized, QueryHasherPairRequest.class );
        Assert.assertEquals( qhp, recovered );
    }
}
