package pl.training.shop.payments.adapters.stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.training.shop.payments.ports.Payment;

import javax.annotation.PostConstruct;

@Aspect
@Component
@Log
@RequiredArgsConstructor
public class RemotePaymentsProcessor {

    private static final String PAYMENTS = "payments";
    private static final String LATEST_PAYMENT = "%s/latest".formatted(PAYMENTS);
    private static final String BASE_URL = "http://localhost:9000";

    private final StreamPaymentMapper mapper;

    private final WebClient.Builder webClientBuilder = WebClient.builder()
            .baseUrl(BASE_URL);

    @AfterReturning(value = "execution(pl.training.shop.payments.ports.Payment pl.training..*.PaymentProcessor.proc*(..))", returning = "payment")
    public void onPayment(Payment payment) {
        webClientBuilder.build()
                .post()
                .uri(PAYMENTS)
                .bodyValue(mapper.toDto(payment))
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .map(mapper::toDomain)
                .subscribe(processedPayment -> log.info("Payment processed: " + processedPayment), exception -> log.warning(exception.toString()));
    }

    @PostConstruct
    public void init() {
        try {
            webClientBuilder.build()
                    .get()
                    .uri(LATEST_PAYMENT)
                    .retrieve()
                    .bodyToFlux(PaymentDto.class)
                    .map(mapper::toDomain)
                    .subscribe(processedPayment -> log.info("New payment processed: " + processedPayment), exception -> log.warning(exception.toString()));
        } catch (Exception exception) {
            log.info("Payments broker not found");
        }
    }

}
