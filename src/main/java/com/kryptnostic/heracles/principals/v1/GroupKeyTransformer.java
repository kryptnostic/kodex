package com.kryptnostic.heracles.principals.v1;

import com.google.common.base.Function;

public class GroupKeyTransformer implements Function<GroupKey, String> {
    private static final GroupKeyTransformer transformer = new GroupKeyTransformer();
    
    @Override
    public String apply(GroupKey input) {
        return input.getRealm() + "." + input.getName(); 
    }

    public static GroupKeyTransformer getInstance() {
        return transformer;
    }
}
