package com.learn.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface Consumer<T, U> {

    void receive();

    void processRecord(ConsumerRecord<T, U> record);
}
