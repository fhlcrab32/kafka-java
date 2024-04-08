package com.learn.kafka;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import com.learn.kafka.consumer.AlbumConsumer;
import com.learn.kafka.consumer.LogConsumer;
import com.learn.kafka.consumer.StateDataConsumer;
import com.learn.kafka.producer.AlbumProducer;
import com.learn.kafka.producer.LogProducer;
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

    private static final boolean isMongoSink = false;

    public static void main(String[] args) {
        KafkaConfig kafkaConfig = KafkaConfig.getInstance();
        if (!isMongoSink) {
            executorService.execute(() -> new AlbumConsumer(kafkaConfig, Topic.CONNECT_DISTRIBUTED).receive());
        }
        executorService.execute(() -> new StateDataConsumer(kafkaConfig, Topic.SIMPLE).receive());
        executorService.execute(() -> new LogConsumer(kafkaConfig, Topic.CONNECT_STANDALONE).receive());


        executorService.execute(() -> new AlbumProducer(kafkaConfig, Topic.CONNECT_DISTRIBUTED).produce());
        executorService.execute(() -> new StateDataProducer(kafkaConfig, Topic.SIMPLE).produce());
        executorService.execute(() -> new LogProducer(kafkaConfig, Topic.CONNECT_STANDALONE).produce());
        shutdownTasks();
    }


    //TODO
    /*private static <T, U> void buildStream(Topic topic, T key, U value) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<?, ?> stream = builder.stream(topic.getName(),
                Consumed.with(Serdes.serdeFrom(key.getClass()), Serdes.serdeFrom(value.getClass())));
       Topology topology = builder.build();
       Properties config = KafkaStreamConfig.getInstance().loadProperties("1");
        try (KafkaStreams streams = new KafkaStreams(topology, config)) {
            streams.start();
        }
    } */

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
