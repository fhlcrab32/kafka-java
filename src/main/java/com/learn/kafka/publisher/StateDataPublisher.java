package com.learn.kafka.publisher;

import com.learn.kafka.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class StateDataPublisher implements Publisher<String, Double> {

    private final KafkaConfig kafkaConfig;

    public StateDataPublisher(KafkaConfig config) {
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
                        System.out.format("Sending message with key: value - %s, %s\n", key, message.toString());
                    } else {
                        System.out.format("Sending failed for key: %s, message: %s", key, message);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
    }
}
