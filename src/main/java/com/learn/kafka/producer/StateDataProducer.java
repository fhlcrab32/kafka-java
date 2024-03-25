package com.learn.kafka.producer;

import com.learn.kafka.config.KafkaConfig;
import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class StateDataProducer implements MessageProducer<String, Double> {

    private final Logger log = LoggerFactory.getLogger(StateDataProducer.class);

    private final KafkaConfig kafkaConfig;

    private final Topic topic;

    public StateDataProducer(
            KafkaConfig config,
            Topic topic) {
        this.kafkaConfig = config;
        this.topic = topic;
    }


    @NotNull
    @Override
    public Map<String, Double> getSourceData() {
        Map <String, Double> sourceData = new HashMap<>();
        List<String> states = Arrays.stream(
                ("AK,AL,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO," +
                        "MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY"
                ).split(",")).toList();
        for(String state: states) {
            sourceData.put(state, new Random().nextDouble() * state.hashCode());
        }
        return sourceData;
    }

    @Override
    public void send(String key, Double message) {
        ProducerRecord<String, Double> producerRecord = new ProducerRecord<>(
                topic.getName(),
                key,
                message
        );
        try(KafkaProducer<String, Double> producer = kafkaConfig.buildProducer(topic)) {
            {
                producer.send(producerRecord, (recordMetadata, e) -> {
                    if (recordMetadata != null) {
                        log.info("Sending message {}: {}", key, message.toString());
                    } else {
                        log.error("Sending failed for key: {}, message: {}", key, message, e);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        log.error("InterruptedException thrown while sleeping", e);
                    }
                });
            }
        }
    }
}
