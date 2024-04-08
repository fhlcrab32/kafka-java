package com.learn.kafka.config;

import com.learn.kafka.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;


public class KafkaConfig {

    private static KafkaConfig INSTANCE;

    private final KafkaConfigFactory kafkaConfigFactory;

    protected KafkaConfig(KafkaConfigFactory kafkaConfigFactory) {
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
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVERS);
        kafkaConfigFactory.buildSerializers(props, topic);
        return props;
    }

    private Properties loadConsumerProperties(Topic topic) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, topic + "_group1");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConstants.AUTO_OFFSET_RESET_DEFAULT);
        kafkaConfigFactory.buildDeserializers(props, topic);
        return props;
    }
}
