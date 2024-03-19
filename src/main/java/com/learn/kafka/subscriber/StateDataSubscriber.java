package com.learn.kafka.subscriber;

import com.learn.kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

public class StateDataSubscriber implements Subscriber<String, Double> {


    private final KafkaConfig kafkaConfig;

    public StateDataSubscriber(KafkaConfig config) {
        this.kafkaConfig = config;
    }


    @Override
    public void receive() {
        String topicName;
        try (KafkaConsumer<String, Double> consumer = kafkaConfig.buildConsumer()) {
            long startTime = System.currentTimeMillis();
            topicName = kafkaConfig.getTopicName();
            consumer.subscribe(Collections.singletonList(topicName));
            System.out.printf("Listening to topic %s\n", topicName);
            while (System.currentTimeMillis() - startTime < 60000) {
                ConsumerRecords<String, Double> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(this::processRecord);
            }
            System.out.printf("===== Stopped consuming from topic %s =====\n\n", topicName);
        }
    }

    @Override
    public void processRecord(ConsumerRecord<String, Double> record) {
        System.out.printf("Received key: %s, value: %s from partition: %s\n",
                record.key(), record.value(), record.partition());
    }
}
