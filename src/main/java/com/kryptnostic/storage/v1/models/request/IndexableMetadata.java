package com.kryptnostic.storage.v1.models.request;

public class IndexableMetadata {
    private String key;
    private String data;

    public IndexableMetadata() {

    }

    public IndexableMetadata(String key, String data) {
        super();
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
