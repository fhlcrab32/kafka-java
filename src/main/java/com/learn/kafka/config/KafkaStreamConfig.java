package com.learn.kafka.config;

import com.learn.kafka.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class KafkaStreamConfig {

    private static KafkaStreamConfig INSTANCE;

    private final KafkaConfigFactory kafkaConfigFactory;

    protected KafkaStreamConfig(KafkaConfigFactory kafkaConfigFactory) {
        this.kafkaConfigFactory = kafkaConfigFactory;
    }

    public synchronized static KafkaStreamConfig getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new KafkaStreamConfig(new KafkaConfigFactory());
        }
        return INSTANCE;
    }

    public <T, U> KafkaConsumer<T, U> buildConsumer(
            String applicationId, Class<T> keySerde, Class<U> valueSerde) {
        return new KafkaConsumer<>(loadProperties(applicationId, keySerde, valueSerde));
    }

    public <T, U> Properties loadProperties(String applicationId, Class<T> keySerde, Class<U> valueSerde) {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVER_MASTER);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.serdeFrom(keySerde));
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.serdeFrom(valueSerde));
        props.put(KafkaConstants.SCHEMA_REGISTRY_URL_KEY, KafkaConstants.SCHEMA_REGISTRY_URL);
        return props;
    }
}
