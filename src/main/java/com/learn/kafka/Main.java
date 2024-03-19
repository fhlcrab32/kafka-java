package com.learn.kafka;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.publisher.Publisher;
import com.learn.kafka.publisher.StateDataPublisher;
import com.learn.kafka.subscriber.StateDataSubscriber;
import com.learn.kafka.subscriber.Subscriber;

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
        Subscriber<String, Double> subscriber = new StateDataSubscriber(config);
        subscriber.receive();
    }

    private static void produceStateData(KafkaConfig config) {
        try {
            Publisher<String, Double> publisher = new StateDataPublisher(config);
            Thread.sleep(5000);
            System.out.println("Sending messages");
            for(String key: publisher.getSourceData()) {
                publisher.send(key, Math.floor(Math.random() * key.hashCode()));
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
