package pl.training.shop.payments.adapters.logging;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.ports.Payment;
import pl.training.shop.payments.ports.PaymentRequest;

@Aspect
@Component
@Log
public class ConsolePaymentsLogger {

    @Pointcut("execution(pl.training.shop.payments.ports.Payment pl.training..*.PaymentProcessor.proc*(..))")
    // @Pointcut("@annotation(pl.training.shop.payments.domain.LogPayment)")
    // @Pointcut("bean(paymentService)")
    void process() {
    }

    @Before("process() && args(paymentRequest)")
    public void onStart(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    @AfterReturning(value = "process()", returning = "payment")
    public void onSuccess(Payment payment) {
        log.info("A new payment of %s has been created".formatted(payment.getValue()));
    }

    @AfterThrowing(value = "process()", throwing = "exception")
    public void onFailure(JoinPoint joinPoint, RuntimeException exception) {
        log.info("Payment processing failed: %s (method: %s)".formatted(
                exception.getClass().getSimpleName(), joinPoint.getSignature()));
    }

    @After("process()")
    public void onFinish() {
        log.info("Payment processing complete");
    }

}
