package com.learn.kafka.publisher;

import java.util.List;

public interface Producer<T,U> {

    List<T> getSourceData();

    void send(T key, U message);
}
