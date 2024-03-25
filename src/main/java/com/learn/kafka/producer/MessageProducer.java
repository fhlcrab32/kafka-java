package com.learn.kafka.producer;

import java.util.Map;

public interface MessageProducer<T,U> {

    Map<T, U> getSourceData();

    void send(T key, U message);
}
