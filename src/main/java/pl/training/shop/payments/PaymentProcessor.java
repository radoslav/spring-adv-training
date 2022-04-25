package pl.training.shop.payments;

import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;

import java.time.Instant;

@Log
public class PaymentProcessor {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated";

    private final PaymentIdGenerator paymentIdGenerator = new PaymentIdGenerator();
    private final PaymentFeeCalculator paymentFeeCalculator = new PaymentFeeCalculator(0.01);
    private final PaymentRepository paymentsRepository = new PaymentRepository();

    public Payment process(PaymentRequest paymentRequest) {
        var totalPaymentValue = calculateTotalPaymentValue(paymentRequest.getValue());
        var payment = Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(totalPaymentValue)
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();
        log.info(createLogEntry(payment));
        return paymentsRepository.save(payment);
    }

    private FastMoney calculateTotalPaymentValue(FastMoney paymentValue) {
        return paymentValue.add(paymentFeeCalculator.calculateFee(paymentValue));
    }

    private String createLogEntry(Payment payment) {
        return String.format(LOG_FORMAT, payment.getValue());
    }

}
