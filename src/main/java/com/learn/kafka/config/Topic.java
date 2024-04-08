package com.learn.kafka.config;

public enum Topic {
    SIMPLE("states"),
    CONNECT_STANDALONE("connect-log"),
    CONNECT_DISTRIBUTED("connect-dist"),
    RAW_TEMP_READINGS("raw-temp-readings"),
    VALIDATED_TEMP_READINGS("validated-temp-readings");

    private final String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() { return  this.name; }
}
