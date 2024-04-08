package com.learn.kafka.constants;

public class KafkaConstants {
    private KafkaConstants() {}
    public static final String BOOTSTRAP_SERVERS = "http://localhost:9092,http://localhost:9093,http://localhost:9094";
    public static final String BOOTSTRAP_SERVER_MASTER = "http://localhost:9092";
    public static String SCHEMA_REGISTRY_URL_KEY = "schema.registry.url";
    public static String SCHEMA_REGISTRY_URL = "http://localhost:8081";
    public static String AUTO_OFFSET_RESET_DEFAULT = "earliest";
}
