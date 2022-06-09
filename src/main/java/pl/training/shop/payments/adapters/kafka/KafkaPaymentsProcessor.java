package pl.training.shop.payments.adapters.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.ports.Payment;

@Aspect
@Component
@Log
@RequiredArgsConstructor
public class KafkaPaymentsProcessor {

    private static final String TOPIC = "main";
    private final KafkaPaymentMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @AfterReturning(value = "execution(pl.training.shop.payments.ports.Payment pl.training..*.PaymentProcessor.proc*(..))", returning = "payment")
    public void onPayment(Payment payment) {
        var paymentDto = mapper.toDto(payment);
        kafkaTemplate.send(TOPIC, "New payment: " + paymentDto);
    }

}
