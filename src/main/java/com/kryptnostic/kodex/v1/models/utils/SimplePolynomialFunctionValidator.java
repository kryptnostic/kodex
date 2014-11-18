package com.kryptnostic.kodex.v1.models.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.kodex.v1.crypto.keys.JacksonKodexMarshaller;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class SimplePolynomialFunctionValidator implements Serializable {
    private static final long                                                      serialVersionUID         = 3325415662614137319L;
    private static final int                                                       DEFAULT_NUMBER_OF_ROUNDS = 1000;
    private static final String                                                    INPUTS_FIELD             = "inputs";
    private static final String                                                    OUTPUTS_FIELD            = "outputs";
    private static final JacksonKodexMarshaller<SimplePolynomialFunctionValidator> marshaller               = new JacksonKodexMarshaller<SimplePolynomialFunctionValidator>(
                                                                                                                    SimplePolynomialFunctionValidator.class );
    private final BitVector[]                                                      inputs;
    private final BitVector[]                                                      outputs;

    @JsonCreator
    public SimplePolynomialFunctionValidator(
            @JsonProperty( INPUTS_FIELD ) BitVector[] inputs,
            @JsonProperty( OUTPUTS_FIELD ) BitVector[] outputs ) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public SimplePolynomialFunctionValidator( SimplePolynomialFunction f ) {
        this( f, DEFAULT_NUMBER_OF_ROUNDS );
    }

    public SimplePolynomialFunctionValidator( SimplePolynomialFunction f, int rounds ) {
        this.inputs = new BitVector[ rounds ];
        this.outputs = new BitVector[ rounds ];
        int inputLength = f.getInputLength();
        for ( int i = 0; i < rounds; ++i ) {
            inputs[ i ] = BitVectors.randomVector( inputLength );
            outputs[ i ] = f.apply( inputs[ i ] );
        }
    }

    @JsonProperty( INPUTS_FIELD )
    public BitVector[] getInputs() {
        return inputs;
    }

    @JsonProperty( OUTPUTS_FIELD )
    public BitVector[] getOutputs() {
        return outputs;
    }

    public boolean validate( SimplePolynomialFunction f ) {
        for ( int i = 0; i < inputs.length; ++i ) {
            if ( !f.apply( inputs[ i ] ).equals( outputs[ i ] ) ) {
                return false;
            }
        }
        return true;
    }

    @JsonIgnore
    public byte[] getBytes() throws IOException {
        return marshaller.toBytes( this );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( inputs );
        result = prime * result + Arrays.hashCode( outputs );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof SimplePolynomialFunctionValidator ) ) {
            return false;
        }
        SimplePolynomialFunctionValidator other = (SimplePolynomialFunctionValidator) obj;
        if ( !Arrays.equals( inputs, other.inputs ) ) {
            return false;
        }
        if ( !Arrays.equals( outputs, other.outputs ) ) {
            return false;
        }
        return true;
    }

    public static SimplePolynomialFunctionValidator fromBytes( byte[] b ) throws IOException {
        return marshaller.fromBytes( b );
    }
}
