package com.learn.kafka.producer;

import java.util.Map;

public interface Producer<T,U> {

    Map<T, U> getSourceData();

    void send(T key, U message);
}
