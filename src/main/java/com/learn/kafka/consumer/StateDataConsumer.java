package com.learn.kafka.consumer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

public class StateDataConsumer implements Consumer<String, Double> {

    private final Logger log = LoggerFactory.getLogger(StateDataConsumer.class);

    private final KafkaConfig kafkaConfig;

    private final Topic topic;

    public StateDataConsumer(KafkaConfig config,
                             Topic topic) {
        this.kafkaConfig = config;
        this.topic = topic;
    }


    @Override
    public void receive() {
        KafkaConsumer<String, Double> consumer = null;
        try {
            consumer = kafkaConfig.buildConsumer(this.topic);
            long startTime = System.currentTimeMillis();
            consumer.subscribe(Collections.singletonList(this.topic.getName()));
            log.info("Listening to topic {}", this.topic);
            while (System.currentTimeMillis() - startTime < 45000) {
                ConsumerRecords<String, Double> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(this::processRecord);
            }
            log.info("Stopped consuming from topic {}", this.topic);
        } finally {
            assert consumer != null;
            consumer.close();
        }
    }

    @Override
    public void processRecord(ConsumerRecord<String, Double> record) {
        log.info("Received key: {}, value: {} from partition: {}",
                record.key(), record.value(), record.partition());
    }
}
