package com.learn.kafka.subscriber;

import com.learn.kafka.config.KafkaConfig;
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

    public StateDataConsumer(KafkaConfig config) {
        this.kafkaConfig = config;
    }


    @Override
    public void receive() {
        String topicName;
        KafkaConsumer<String, Double> consumer = null;
        try {
            consumer = kafkaConfig.buildConsumer();
            long startTime = System.currentTimeMillis();
            topicName = kafkaConfig.getTopicName();
            consumer.subscribe(Collections.singletonList(topicName));
            log.info("Listening to topic {}", topicName);
            while (System.currentTimeMillis() - startTime < 45000) {
                ConsumerRecords<String, Double> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(this::processRecord);
            }
            log.info("Stopped consuming from topic {}", topicName);
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
