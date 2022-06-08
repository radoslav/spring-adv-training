package pl.training.payments.broker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private static final String DEFAULT_STATUS = "PROCESSED";

    private final PaymentsMapper mapper;
    private final PaymentsRepository paymentsRepository;
    private final Sinks.Many<PaymentDomain> paymentDomainFlux = Sinks.many().replay().latest();

    public Flux<PaymentDomain> getLatestPayment() {
        return paymentDomainFlux.asFlux();
    }

    public Flux<PaymentDomain> getPayments() {
        return paymentsRepository.findAll()
                .map(mapper::toDomain);
    }

    public Mono<PaymentDomain> process(Mono<PaymentDomain> paymentDomainMono) {
        return paymentDomainMono.map(this::toProcessed)
                .flatMap(this::save)
                .doOnNext(paymentDomainFlux::tryEmitNext);
    }

    private PaymentDomain toProcessed(PaymentDomain paymentDomain) {
        return paymentDomain.withStatus(DEFAULT_STATUS);
    }

    private Mono<PaymentDomain> save(PaymentDomain paymentDomain) {
        var paymentDocument = mapper.toDocument(paymentDomain);
        return paymentsRepository.save(paymentDocument)
                .map(mapper::toDomain);
    }

}
