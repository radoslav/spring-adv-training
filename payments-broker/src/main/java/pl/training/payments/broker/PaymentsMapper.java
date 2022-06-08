package pl.training.payments.broker;

import org.mapstruct.Mapper;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.*;

@Mapper(componentModel = "spring")
public interface PaymentsMapper {

    PaymentDocument toDocument(PaymentDomain paymentDomain);

    PaymentDomain toDomain(PaymentDocument paymentDocument);

    PaymentDto toDto(PaymentDomain paymentDomain);

    PaymentDomain toDomain(PaymentDto paymentDto);

    default Mono<ServerResponse> toServerResponse(Flux<PaymentDomain> paymentDomainFlux) {
        return ServerResponse.ok()
                //.contentType(APPLICATION_STREAM_JSON)
                .contentType(APPLICATION_NDJSON)
                .body(paymentDomainFlux.map(this::toDto), PaymentDto.class);
    }

    default Mono<ServerResponse> toServerResponse(Mono<PaymentDomain> paymentDomainMono) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(paymentDomainMono.map(this::toDto), PaymentDto.class);
    }

    default Mono<PaymentDomain> toDomain(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PaymentDto.class).map(this::toDomain);
    }

}
