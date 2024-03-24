package com.learn.kafka.producer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import com.learn.kafka.model.Album;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AlbumProducer implements Producer<Double, Album> {

    private final Logger log = LoggerFactory.getLogger(AlbumProducer.class);

    private final KafkaConfig kafkaConfig;

    private final Topic topic;

    public AlbumProducer(KafkaConfig kafkaConfig, Topic topic) {
        this.kafkaConfig = kafkaConfig;
        this.topic = topic;
    }


    @Override
    public Map<Double, Album> getSourceData() {
        Album album = Album.newBuilder()
                .setName("Nadodikkattu")
                .setYear(1990).build();
        double key = Math.floor(Math.random()* album.getYear());
        return Map.of(key, album);
    }

    @Override
    public void send(Double key, Album message) {
        ProducerRecord<Double, Album> producerRecord = new ProducerRecord<>(
                topic.getName(),
                key,
                message
        );
        try(KafkaProducer<Double, Album> producer = kafkaConfig.buildProducer(topic)) {
            {
                //TODO remove duplicate
                producer.send(producerRecord, (recordMetadata, e) -> {
                    if (recordMetadata != null) {
                        log.info("Sending message {}: {}", key, message);
                    } else {
                        log.error("Sending failed for key: {}, message: {}", key, message, e);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        log.error("InterruptedException thrown while sleeping", e);
                    }
                });
            }
        }
    }
}
