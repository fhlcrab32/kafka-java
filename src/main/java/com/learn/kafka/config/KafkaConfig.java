package com.learn.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;


public class KafkaConfig {

    private static KafkaConfig INSTANCE;

    private final KafkaConfigFactory kafkaConfigFactory;

    private final String bootstrapServers = "http://localhost:9092,http://localhost:9093,http://localhost:9094";

    private KafkaConfig(KafkaConfigFactory kafkaConfigFactory) {
        this.kafkaConfigFactory = kafkaConfigFactory;
    }

    public synchronized static KafkaConfig getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new KafkaConfig(new KafkaConfigFactory());
        }
        return INSTANCE;
    }

    public <T, U> KafkaProducer<T, U> buildProducer(Topic topic) {
        return new KafkaProducer<>(loadProducerProperties(topic));
    }

    public <T, U> KafkaConsumer<T, U> buildConsumer(Topic topic) {
        return new KafkaConsumer<>(loadConsumerProperties(topic));
    }

    private Properties loadProducerProperties(Topic topic) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        kafkaConfigFactory.buildSerializers(properties, topic);
        return properties;
    }

    private Properties loadConsumerProperties(Topic topic) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConfigFactory.buildDeserializers(properties, topic);
        return properties;
    }
}
