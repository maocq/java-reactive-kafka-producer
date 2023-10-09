package co.com.bancolombia.kafkaproducer;

import co.com.bancolombia.model.information.Information;
import co.com.bancolombia.model.information.gateways.InformationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class KafkaProducer implements InformationGateway {

    private final KafkaSender<String, Information> kafkaSender;
    private final ReactiveKafkaProducerTemplate<String, Information> kafkaTemplate;

    public void emit() {
        sendKafkaSender();
        sendkafkaTemplate();
    }

    private void sendKafkaSender() {
        var info = Information.builder().id(1).message("foo").build();

        Mono<SenderRecord<String, Information, String>> record =
                Mono.just(SenderRecord.create("my-topic", null, null, "key", info, "key"));

        /*Flux<SenderRecord<String, Information, String>> outboundFlux =
                Flux.range(1, 5)
                        .map(Object::toString)
                        .map(key -> SenderRecord.create("my-topic", null, null, key, info, key));*/

        kafkaSender.send(record)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(r -> System.out.printf("Message %s send response: %s\n", r.correlationMetadata(), r.recordMetadata()))
                .subscribe();
    }

    private void sendkafkaTemplate() {
        var info = Information.builder().id(1).message("bar").build();
        kafkaTemplate.send("my-topic", info)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(r -> System.out.printf("Message %s send response: %s\n", r.correlationMetadata(), r.recordMetadata()))
                .subscribe();
    }
}
