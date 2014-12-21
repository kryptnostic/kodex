package com.kryptnostic.heracles.authorization.v1;

import java.io.IOException;
import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class PermissionsTest {
    private static ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.registerModule( new AfterburnerModule() );
        mapper.registerModule( new GuavaModule() );
    }
    
    @Test
    public void serdes() throws JsonParseException, JsonMappingException, IOException {
        Permissions p = new Permissions( EnumSet.of( Permission.AUDIT ,Permission.READ ,Permission.WRITE, Permission.OWNER) );
        String value = mapper.writeValueAsString( p );
        Permissions actual = mapper.readValue( value , Permissions.class );
        Assert.assertEquals( p , actual );
    }
}
