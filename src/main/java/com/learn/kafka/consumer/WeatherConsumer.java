package com.learn.kafka.consumer;

import com.learn.kafka.config.KafkaStreamConfig;
import com.learn.kafka.config.Topic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static com.learn.kafka.config.Topic.VALIDATED_TEMP_READINGS;

public class WeatherConsumer implements StreamConsumer<String, Double> {

    private final Logger log = LoggerFactory.getLogger(StateDataConsumer.class);
    private final KafkaStreamConfig kafkaStreamConfig;
    private final Topic topic;
    private final StreamsBuilder  streamsBuilder;

    public WeatherConsumer(KafkaStreamConfig config,
                             Topic topic) {
        this.kafkaStreamConfig = config;
        this.topic = topic;
        this.streamsBuilder = new StreamsBuilder();
    }



    @Override
    public void receive() {
        KafkaConsumer<String, Double> consumer = null;
        try {
            consumer = kafkaStreamConfig.buildConsumer("weather.filter",
                    String.class, Double.class);
            process(consumer, this.topic, log::info);
        } finally {
            assert consumer != null;
            consumer.close();
        }
    }

    //TODO
    @Override
    public void process(KafkaConsumer<String, Double> consumer, Topic topic, Consumer<String> logFunction) {
        KStream<String, Double> messages = streamsBuilder.stream(topic.toString());
        KStream<String, Double> validatedReadings = messages.filter((key, value) -> value > -50 && value < 130);
        validatedReadings.to(VALIDATED_TEMP_READINGS.toString());
        Topology topology = streamsBuilder.build();
        log.info(topology.describe().toString());
        //KafkaStreams streams = new KafkaStreams(topology);

    }
}
