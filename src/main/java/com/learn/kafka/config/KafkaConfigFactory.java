package com.learn.kafka.config;

import com.learn.kafka.constants.KafkaConstants;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfigFactory {

    void buildSerializers(Properties properties, Topic topic) {
        switch (topic) {
            case SIMPLE -> {
                properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class.getName());
                properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        DoubleSerializer.class.getName());
            }
            case CONNECT_STANDALONE -> {
                properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        DoubleSerializer.class.getName());
                properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class.getName());
            }
            case CONNECT_DISTRIBUTED ->   {
                properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        DoubleSerializer.class.getName());
                properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        KafkaAvroSerializer.class.getName());
                properties.setProperty(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                        KafkaConstants.SCHEMA_REGISTRY_URL);
            }
        }
    }

    public void buildDeserializers(Properties properties, Topic topic) {
       switch (topic){
           case SIMPLE -> {
                properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                        StringDeserializer.class.getName());
                properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                        DoubleDeserializer.class.getName());
            }
           case CONNECT_STANDALONE -> {
                properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                        DoubleDeserializer.class.getName());
                properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                        StringDeserializer.class.getName());
            }
           case CONNECT_DISTRIBUTED ->   {
               properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                       DoubleDeserializer.class.getName());
               properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                       KafkaAvroDeserializer.class.getName());
               properties.setProperty(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                       KafkaConstants.SCHEMA_REGISTRY_URL);
           }
        }
    }
}
