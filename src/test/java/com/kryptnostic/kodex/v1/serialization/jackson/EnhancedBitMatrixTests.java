package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.linear.EnhancedBitMatrix;
import com.kryptnostic.utils.SerializationTestUtils;

public class EnhancedBitMatrixTests extends SerializationTestUtils {
    @Test
    public void serializeEbmTest() throws IOException {
        EnhancedBitMatrix matrix = EnhancedBitMatrix.randomMatrix(10, 10);
        String expected = mapper.writeValueAsString(matrix);
        Assert.assertEquals(expected, serialize(matrix));
    }
    
    @Test
    public void deserializeEbmTest() throws IOException {
        EnhancedBitMatrix matrix = EnhancedBitMatrix.randomMatrix(10, 10);
        String serialized = mapper.writeValueAsString(matrix);
        EnhancedBitMatrix recovered = mapper.readValue(serialized, EnhancedBitMatrix.class);
        Assert.assertEquals(matrix, recovered);
    }
    
    
}
