package pl.training.shop.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiTemplate;
import pl.training.shop.payments.domain.DefaultPaymentServiceFactory;
import pl.training.shop.payments.domain.PaymentServiceDecorator;
import pl.training.shop.payments.ports.PaymentRepository;
import pl.training.shop.payments.ports.PaymentService;
import pl.training.shop.payments.ports.PaymentServiceFactory;
import pl.training.shop.payments.ports.TimeProvider;

import javax.naming.NamingException;

@Configuration
public class PaymentsConfiguration {

    private static final PaymentServiceFactory PAYMENT_SERVICE_FACTORY = new DefaultPaymentServiceFactory();

    @Bean
    public PaymentService paymentService(PaymentRepository paymentRepository, TimeProvider timeProvider) {
        return PAYMENT_SERVICE_FACTORY.create(paymentRepository, timeProvider);
    }

    @Primary
    @Bean
    public PaymentService paymentServiceDecorator(PaymentService paymentService) {
        return new PaymentServiceDecorator(paymentService);
    }

    @Primary
    @Bean
    public TimeProvider timeProvider(JndiTemplate jndiTemplate) throws NamingException {
        return jndiTemplate.lookup("java:/jee-1.0-SNAPSHOT/SystemTimeProvider!pl.training.shop.payments.ports.TimeProvider", TimeProvider.class);
    }

}
