package pl.training.payments.broker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class PaymentRestAdapter {

    private final PaymentsService paymentsService;
    private final PaymentsMapper mapper;

    public Mono<ServerResponse> getPayments(ServerRequest serverRequest) {
        return mapToResponse(paymentsService.getPayments());
    }

    private Mono<ServerResponse> mapToResponse(Flux<PaymentDomain> paymentDomainFlux) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(paymentDomainFlux.map(mapper::toDto), PaymentDto.class);
    }

}
