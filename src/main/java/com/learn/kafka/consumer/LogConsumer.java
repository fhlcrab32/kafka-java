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

public class LogConsumer implements MessageConsumer<Double, String> {

    private final Logger log = LoggerFactory.getLogger(LogConsumer.class);

    private final KafkaConfig kafkaConfig;

    private final Topic topic;

    public LogConsumer(KafkaConfig config,
                       Topic topic) {
        this.kafkaConfig = config;
        this.topic = topic;
    }

    @Override
    public void receive() {
        KafkaConsumer<Double, String> consumer = null;
        try {
            consumer = kafkaConfig.buildConsumer(this.topic);
            long startTime = System.currentTimeMillis();
            consumer.subscribe(Collections.singletonList(this.topic.getName()));
            log.info("Listening to topic {}", this.topic);
            while (System.currentTimeMillis() - startTime < 45000) {
                ConsumerRecords<Double, String> records = consumer.poll(Duration.ofMillis(100));
                for(ConsumerRecord<Double, String> record : records) {
                    process(record, log::info);
                }
            }
            log.info("Stopped consuming from topic {}", this.topic);
        } finally {
            assert consumer != null;
            consumer.close();
        }
    }
}
