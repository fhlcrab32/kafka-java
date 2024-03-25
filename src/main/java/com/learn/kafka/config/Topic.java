package com.learn.kafka.config;

public enum Topic {
    SIMPLE("states"),
    CONNECT_STANDALONE("connect-log"),
    CONNECT_DISTRIBUTED("connect-dist");

    private final String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() { return  this.name; }
}
