package com.learn.kafka;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.publisher.Producer;
import com.learn.kafka.publisher.StateDataProducer;
import com.learn.kafka.subscriber.StateDataConsumer;
import com.learn.kafka.subscriber.Consumer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static void main(String[] args) {
        KafkaConfig config = KafkaConfig.getInstance();
        executorService.execute( () -> consumeStateData(config));

        executorService.execute(() -> produceStateData(config));
        shutdownTasks();

    }

    private static void consumeStateData(KafkaConfig config) {
        Consumer<String, Double> consumer = new StateDataConsumer(config);
        consumer.receive();
    }

    private static void produceStateData(KafkaConfig config) {
        try {
            Producer<String, Double> producer = new StateDataProducer(config);
            Thread.sleep(5000);
            System.out.println("Sending messages");
            for(String key: producer.getSourceData()) {
                producer.send(key, Math.floor(Math.random() * key.hashCode()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void shutdownTasks() {
        try {
            executorService.shutdown();
            boolean completed = executorService.awaitTermination(40, TimeUnit.SECONDS);
            if(!completed) {
                List<Runnable> incompleteTasks = executorService.shutdownNow();
                if (!incompleteTasks.isEmpty()) {
                    System.out.printf("%d task(s) could not be completed!", incompleteTasks.size());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
