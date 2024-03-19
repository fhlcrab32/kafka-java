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

    public <T, U> KafkaConsumer<T, U> buildConsumer() {
        return new KafkaConsumer<>(loadConsumerProperties());
    }

    private Properties loadProducerProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());
        return properties;
    }

    private Properties loadConsumerProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DoubleDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }

    public String getTopicName() {
        return "states";
    }
}
