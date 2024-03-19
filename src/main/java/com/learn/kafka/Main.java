package com.learn.kafka;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.publisher.Producer;
import com.learn.kafka.publisher.StateDataProducer;
import com.learn.kafka.subscriber.StateDataConsumer;
import com.learn.kafka.subscriber.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

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
            Thread.sleep(1000);
            log.info("Sending messages to topic {}", config.getTopicName());
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
            boolean completed = executorService.awaitTermination(60, TimeUnit.SECONDS);
            if(!completed) {
                List<Runnable> incompleteTasks = executorService.shutdownNow();
                if (!incompleteTasks.isEmpty()) {
                    log.warn("{} task(s) could not be completed!", incompleteTasks.size());
                }
            }
            log.info("Shutdown complete");
        } catch (InterruptedException e) {
            log.error("InterruptedException thrown while shutting down", e);
        }
    }

}
