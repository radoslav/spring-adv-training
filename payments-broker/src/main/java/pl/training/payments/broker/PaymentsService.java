package pl.training.payments.broker;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.util.Random;

import static java.time.Duration.ofSeconds;
import static reactor.core.publisher.Flux.combineLatest;

@Service
@Log
@RequiredArgsConstructor
public class PaymentsService implements ApplicationRunner {

    private static final String DEFAULT_STATUS = "PROCESSED";

    private final PaymentsMapper mapper;
    private final PaymentsRepository paymentsRepository;
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final Sinks.Many<PaymentDomain> paymentDomainFlux = Sinks.many().replay().latest();
    private final Random random = new Random();
    private static final double MAX_RATE_FLUCTUATION = 0.3;
    private static final int RATE_UPDATE_INTERVAL_IN_SECONDS = 3;

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

    public Flux<BigDecimal> getExchangeRates() {
        var interval =  Flux.interval(ofSeconds(RATE_UPDATE_INTERVAL_IN_SECONDS));
        var values = Flux.generate(this::generateNewRate).map(BigDecimal::valueOf);
        return interval.zipWith(values).map(Tuple2::getT2);
    }

    public Mono<BigDecimal> exchange(Mono<BigDecimal> valueMono) {
        return valueMono
                .zipWith(getExchangeRates().next())
                .map(tuple -> new ExchangeLogEntryDocument(tuple.getT1(), tuple.getT2()))
                .flatMap(exchangeRatesRepository::save)
                .map(ExchangeLogEntryDocument::getExchangeValue);
    }

    private void generateNewRate(SynchronousSink<Double> sink) {
        sink.next(random.nextDouble(MAX_RATE_FLUCTUATION) * (random.nextBoolean() ? -1 : 1));
    }

    @Override
    public void run(ApplicationArguments args) {
        exchange(Mono.just(BigDecimal.TEN))
                .subscribe(result -> log.info("Exchange value: " + result), exception -> log.warning(exception.toString()));
    }

}
