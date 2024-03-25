package com.learn.kafka;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import com.learn.kafka.consumer.AlbumConsumer;
import com.learn.kafka.consumer.LogConsumer;
import com.learn.kafka.consumer.MessageConsumer;
import com.learn.kafka.consumer.StateDataConsumer;
import com.learn.kafka.model.Album;
import com.learn.kafka.producer.AlbumProducer;
import com.learn.kafka.producer.LogProducer;
import com.learn.kafka.producer.MessageProducer;
import com.learn.kafka.producer.StateDataProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    public static void main(String[] args) {
        KafkaConfig config = KafkaConfig.getInstance();
        executorService.execute(() ->  new AlbumConsumer(config, Topic.CONNECT_DISTRIBUTED).receive());
        executorService.execute(() -> consumeStateData(config));
        executorService.execute(() ->  new LogConsumer(config, Topic.CONNECT_STANDALONE).receive());


        executorService.execute(() -> produceAlbumData(config));
        executorService.execute(() -> produceStateData(config));
        executorService.execute(() -> produceLogs(config));

        shutdownTasks();
    }

    private static void produceAlbumData(KafkaConfig config) {
        try {
            MessageProducer<Double, Album> producer = new AlbumProducer(config, Topic.CONNECT_DISTRIBUTED);
            Thread.sleep(100);
            log.info("Sending messages to topic {}", Topic.CONNECT_DISTRIBUTED);
            producer.getSourceData().forEach(producer::send);
        } catch (InterruptedException e) {
            log.error("InterruptedException occurred: {}", e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    private static void consumeStateData(KafkaConfig config) {
        MessageConsumer<String, Double> consumer = new StateDataConsumer(config, Topic.SIMPLE);
        consumer.receive();
    }

    private static void produceStateData(KafkaConfig config) {
        try {
            MessageProducer<String, Double> producer = new StateDataProducer(config, Topic.SIMPLE);
            Thread.sleep(1000);
            log.info("Sending messages to topic {}", Topic.SIMPLE);
            producer.getSourceData().forEach(producer::send);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void produceLogs(KafkaConfig config) {
        try {
            MessageProducer<Double, String> producer = new LogProducer(config, Topic.CONNECT_STANDALONE);
            Thread.sleep(1000);
            log.info("Sending messages to topic {}", Topic.CONNECT_STANDALONE);
            producer.getSourceData().forEach(producer::send);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void shutdownTasks() {
        try {
            executorService.shutdown();
            boolean completed = executorService.awaitTermination(120, TimeUnit.SECONDS);
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
