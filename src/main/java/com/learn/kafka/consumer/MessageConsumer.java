package com.learn.kafka.consumer;

import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Consumer;

public interface MessageConsumer<T, U> {

    void receive();

    default void process(KafkaConsumer<T, U> consumer, Topic topic, Consumer<String> logFunction) {
        long startTime = System.currentTimeMillis();
        consumer.subscribe(Collections.singletonList(topic.getName()));
        logFunction.accept(String.format("Listening to topic %s", topic));
        while (System.currentTimeMillis() - startTime < 45000) {
            ConsumerRecords<T, U> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<T, U> record : records) {
                logFunction.accept(String.format("Received key: %s, value: %s from partition: %s",
                        record.key(), record.value(),
                        record.partition()));
            }
        }
        logFunction.accept(String.format("Stopped consuming from topic %s", topic));
    }
}
