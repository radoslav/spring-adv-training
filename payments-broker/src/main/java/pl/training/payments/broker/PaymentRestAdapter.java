package pl.training.payments.broker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentRestAdapter {

    private final PaymentsService paymentsService;
    private final PaymentsMapper mapper;

    public Mono<ServerResponse> getPayments(ServerRequest serverRequest) {
        return mapper.toServerResponse(paymentsService.getPayments());
    }

    public Mono<ServerResponse> getLatestPayment(ServerRequest serverRequest) {
        return mapper.toServerResponse(paymentsService.getLatestPayment());
    }

    public Mono<ServerResponse> process(ServerRequest serverRequest) {
        var result = mapper.toDomain(serverRequest)
                .map(Mono::just)
                .flatMap(paymentsService::process);
        return mapper.toServerResponse(result);
    }

    public Mono<ServerResponse> getExchangeRates(ServerRequest serverRequest) {
        return mapper.toServerResponseValues(paymentsService.getExchangeRates());
    }

}
