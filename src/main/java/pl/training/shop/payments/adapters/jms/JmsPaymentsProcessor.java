package pl.training.shop.payments.adapters.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.ports.Payment;

import javax.jms.Topic;

@Aspect
@Component
@Log
@RequiredArgsConstructor
public class JmsPaymentsProcessor {

    private final JmsPaymentMapper mapper;
    private final JmsTemplate jmsTemplate;
    private final Topic trainingTopic;

    @AfterReturning(value = "execution(pl.training.shop.payments.ports.Payment pl.training..*.PaymentProcessor.proc*(..))", returning = "payment")
    public void onPayment(Payment payment) {
        var paymentDto = mapper.toDto(payment);
        jmsTemplate.send(trainingTopic, session -> session.createObjectMessage(paymentDto));
    }

}
