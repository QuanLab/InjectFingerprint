package com.vccorp.bigdata.LRUCache;


public class UserAgent {
    String key;
    String value;
    UserAgent pre;
    UserAgent next;

    public UserAgent(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
