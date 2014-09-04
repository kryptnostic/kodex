package com.kryptnostic.kodex.v1.security;

public class SecurityConfiguration<A, B> {
    private final A publicKey;
    private final B privateKey;

    public SecurityConfiguration(A publicKey) {
        this.publicKey = publicKey;
        this.privateKey = null;
    }

    public SecurityConfiguration(A publicKey, B privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public A getPublicKey() {
        return publicKey;
    }

    public B getPrivateKey() {
        return privateKey;
    } 
}
