package com.learn.kafka.consumer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            process(consumer, this.topic, log::info);
        } finally {
            assert consumer != null;
            consumer.close();
        }
    }
}
