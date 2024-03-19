package com.learn.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class KafkaConfig {

    private static KafkaConfig INSTANCE;

    private final String bootstrapServers = "http://localhost:9092,http://localhost:9093,http://localhost:9094";

    private KafkaConfig() { }

    public synchronized static KafkaConfig getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new KafkaConfig();
        }
        return INSTANCE;
    }

    public <T, U> KafkaProducer<T, U> buildProducer() {
        return new KafkaProducer<>(loadProducerProperties());
    }

    public <T> KafkaConsumer<String, T> buildConsumer() {
        return new KafkaConsumer<>(loadConsumerProperties());
    }

    private Properties loadProducerProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());
        return properties;
    }

    private Properties loadConsumerProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DoubleDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        return properties;
    }

    public String getTopicName() {
        return "states";
    }
}
