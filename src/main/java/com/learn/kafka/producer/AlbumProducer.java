package com.learn.kafka.producer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import com.learn.kafka.model.Album;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AlbumProducer implements MessageProducer<Double, Album> {

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
        try(KafkaProducer<Double, Album> producer = kafkaConfig.buildProducer(topic)) {
            send(this.topic, key, message, producer, log);
        } catch (SerializationException | InterruptException e) {
            log.error("Exception occurred: {}",e.getMessage(), e);
        } catch (Exception e) {
            log.error("Generic Exception occurred: {}",e.getMessage(), e);
        }
    }
}
