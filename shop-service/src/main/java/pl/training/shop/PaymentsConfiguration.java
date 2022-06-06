package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.domain.DefaultPaymentsServiceFactory;
import pl.training.payments.ports.PaymentsServiceFactory;
import pl.training.payments.ports.input.GetPaymentUseCase;
import pl.training.payments.ports.input.ProcessPaymentUseCase;
import pl.training.payments.ports.output.PaymentsReader;
import pl.training.payments.ports.output.PaymentsWriter;
import pl.training.payments.ports.output.TimeProvider;

@Configuration
public class PaymentsConfiguration {

    private static final PaymentsServiceFactory PAYMENT_SERVICE_FACTORY = new DefaultPaymentsServiceFactory();

    @Bean
    public GetPaymentUseCase getPaymentUseCase(PaymentsReader paymentsReader) {
        return PAYMENT_SERVICE_FACTORY.create(paymentsReader);
    }

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(PaymentsWriter paymentsWriter, TimeProvider timeProvider) {
        return PAYMENT_SERVICE_FACTORY.create(paymentsWriter, timeProvider);
    }

}
