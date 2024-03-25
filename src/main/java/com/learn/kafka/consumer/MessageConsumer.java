package com.learn.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.function.Consumer;

public interface MessageConsumer<T, U> {

    void receive();

    default void process(ConsumerRecord<T, U> record, Consumer<String> logFunction) {
        String logStr = String.format("Received key: %s, value: %s from partition: %s",
                record.key(), record.value(),
                record.partition());
        logFunction.accept(logStr);
    }
}
