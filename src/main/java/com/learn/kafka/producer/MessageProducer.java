package com.learn.kafka.producer;

import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;

import java.util.Map;

public interface MessageProducer<T,U> {

    Map<T, U> getSourceData();

    void send(T key, U message);

    default void send(Topic topic, T key, U message, KafkaProducer<T, U> producer,
                      Logger log) {
        ProducerRecord<T, U> producerRecord = new ProducerRecord<>(
                topic.getName(),
                key,
                message
        );
        producer.send(producerRecord, (recordMetadata, e) -> {
            if (recordMetadata != null) {
                log.info("Sending message {}: {}", key, message.toString());
            } else {
                log.error("Sending failed for key: {}, message: {}", key, message, e);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                log.error("InterruptedException thrown while sleeping", e);
            }
        });
    }
}
