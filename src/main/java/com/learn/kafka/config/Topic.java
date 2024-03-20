package com.learn.kafka.config;

public enum Topic {
    SIMPLE("states"),
    CONNECT("connectlog");

    private final String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() { return  this.name; }
}
