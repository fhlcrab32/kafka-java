package com.learn.kafka.consumer;

import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.function.Consumer;

public interface StreamConsumer<T, U> {

    void receive();

    void process(KafkaConsumer<T, U> consumer, Topic topic, Consumer<String> logFunction);
}
