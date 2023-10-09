package co.com.bancolombia.kafkaproducer;

import co.com.bancolombia.model.information.Information;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConf {

    @Bean
    public KafkaSender<String, Information> kafkaSenderConfig() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        SenderOptions<String, Information> senderOptions =
                SenderOptions.create(producerProps)
                //        .maxInFlight(1024)
                ;
        //return KafkaSender.create(senderOptions);

        return KafkaSender.create(SenderOptions.create(producerProps));
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, Information> kafkaTemplateConfig(KafkaProperties properties) {
        Map<String, Object> producerProps = properties.buildProducerProperties();
        SenderOptions<String, Information> senderOptions = SenderOptions.create(producerProps);

        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }
}
