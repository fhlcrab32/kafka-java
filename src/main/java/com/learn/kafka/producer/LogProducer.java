package com.learn.kafka.producer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class LogProducer implements MessageProducer<Double, String> {

    private final Logger log = LoggerFactory.getLogger(LogProducer.class);

    private final KafkaConfig kafkaConfig;

    private final Topic topic;

    public LogProducer(KafkaConfig kafkaConfig, Topic topic) {
        this.kafkaConfig = kafkaConfig;
        this.topic = topic;
    }

    @Override
    public Map<Double, String> getSourceData() {
        Map<Double, String> sourceData = new HashMap<>();
        for(int i = 0; i < 100; i++) {
            sourceData.put( i * 1.0, "value: " + i);
        }
        return sourceData;
    }

    @Override
    public void send(Double key, String message) {
        try(KafkaProducer<Double, String> producer = kafkaConfig.buildProducer(topic)) {
            send(this.topic, key, message, producer, log);
        } catch (SerializationException | InterruptException e) {
            log.error("Exception occurred: {}",e.getMessage(), e);
        } catch (Exception e) {
            log.error("Generic Exception occurred: {}",e.getMessage(), e);
        }
    }
}
