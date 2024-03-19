package com.learn.kafka.publisher;

import com.learn.kafka.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class StateDataProducer implements Producer<String, Double> {

    private final Logger log = LoggerFactory.getLogger(StateDataProducer.class);

    private final KafkaConfig kafkaConfig;

    public StateDataProducer(KafkaConfig config) {
        this.kafkaConfig = config;
    }


    @NotNull
    @Override
    public List<String> getSourceData() {
        return Arrays.stream(("AK,AL,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO," +
                "MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY"
        ).split(",")).toList();
    }

    @Override
    public void send(String key, Double message) {

        ProducerRecord<String, Double> producerRecord = new ProducerRecord<>(
                kafkaConfig.getTopicName(),
                key,
                message
        );
       try(KafkaProducer<String, Double> producer = kafkaConfig.buildProducer()) {
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
