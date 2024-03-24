package com.learn.kafka.consumer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import com.learn.kafka.model.Album;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

public class AlbumConsumer implements Consumer<Double, Album> {

    private final Logger log = LoggerFactory.getLogger(LogConsumer.class);

    private final KafkaConfig kafkaConfig;

    private final Topic topic;

    public AlbumConsumer(KafkaConfig config,
                         Topic topic) {
        this.kafkaConfig = config;
        this.topic = topic;
    }


    @Override
    public void receive() {
        KafkaConsumer<Double, Album> consumer = null;
        try {
            consumer = kafkaConfig.buildConsumer(this.topic);
            long startTime = System.currentTimeMillis();
            consumer.subscribe(Collections.singletonList(this.topic.getName()));
            log.info("Listening to topic {}", this.topic);
            while (System.currentTimeMillis() - startTime < 45000) {
                ConsumerRecords<Double, Album> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(this::processRecord);
            }
            log.info("Stopped consuming from topic {}", this.topic);
        } catch (Exception e) {
          log.error("Error occurred: {}", e.getMessage());
        } finally {
            assert consumer != null;
            consumer.close();
        }
    }

    @Override
    public void processRecord(ConsumerRecord<Double, Album> record) {
        log.info("Received key: {}, value: {} from partition: {}",
                record.key(), record.value(), record.partition());
    }

}
