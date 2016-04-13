package com.kryptnostic.directory.v1.domain;

public enum DomainPolicy {
    Public,
    Discoverable,
    Private,
    BlockOutgoingSharesByEmail,
    BlockIncomingSharesByEmail;
}
