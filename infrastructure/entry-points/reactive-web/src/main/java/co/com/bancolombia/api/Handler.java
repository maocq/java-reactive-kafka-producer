package co.com.bancolombia.api;

import co.com.bancolombia.model.information.gateways.InformationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final InformationGateway informationGateway;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        informationGateway.emit();

        return ServerResponse.ok().bodyValue("Hello");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }
}
