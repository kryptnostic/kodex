package com.kryptnostic.directory.v1;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class UserListingScroll {
    public static final int PAGE_SIZE_BASE_MULTIPLE = 100;
    private final long      page;
    private final Set<UUID> users;
    private final int       scrollInstance;

    @JsonCreator
    public UserListingScroll(
            @JsonProperty( Names.SCROLL_INSTANCE_FIELD ) int scrollInstance,
            @JsonProperty( Names.PAGE_FIELD ) long page,
            @JsonProperty( Names.USERS_FIELD ) Set<UUID> users ) {
        this.scrollInstance = scrollInstance;
        this.page = page;
        this.users = users;
    }

    @JsonProperty( Names.SCROLL_INSTANCE_FIELD )
    public long getPage() {
        return page;
    }

    @JsonProperty( Names.PAGE_FIELD )
    public Set<UUID> getUsers() {
        return users;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public int getScrollInstance() {
        return scrollInstance;
    }

}
