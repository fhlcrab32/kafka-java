package com.learn.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public class KafkaPublisher {

    public boolean sendMessage(String key, Integer message) {
        KafkaProducer<String, Integer> producer = KafkaConfig.build();
        ProducerRecord<String, Integer> producerRecord = new ProducerRecord<>(
                KafkaConfig.TOPIC_NAME,
                key,
                message
        );
        producer.send(producerRecord, (recordMetadata, e) -> {

        });

        return true;
    }
}
