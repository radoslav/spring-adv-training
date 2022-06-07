package pl.training.shop.payments.domain;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import pl.training.shop.commons.Page;
import pl.training.shop.commons.ResultPage;
import pl.training.shop.commons.aop.Length;
import pl.training.shop.commons.aop.Lock;
import pl.training.shop.commons.aop.LogExecutionTime;
import pl.training.shop.commons.aop.Retry;
import pl.training.shop.payments.ports.*;

import static pl.training.shop.commons.aop.Lock.LockType.WRITE;

@RequiredArgsConstructor
public class PaymentProcessor implements PaymentService {

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.CONFIRMED;

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    private final TimeProvider timeProvider;

    @Retry(attempts = 4)
    // @Lock(type = WRITE)
    // @LogExecutionTime
    // @LogPayment
    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var paymentValue = calculatePaymentValue(paymentRequest.getValue());
        var payment = createPayment(paymentValue);
        return paymentsRepository.save(payment);
        // throw new RuntimeException();
    }

    private Payment createPayment(FastMoney paymentValue) {
        return Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentValue)
                .timestamp(timeProvider.getTimestamp())
                .status(DEFAULT_PAYMENT_STATUS)
                .build();
    }

    private FastMoney calculatePaymentValue(FastMoney paymentValue) {
        return paymentValue.add(paymentFeeCalculator.calculateFee(paymentValue));
    }

    @Override
    public Payment getById(@Length String id) {
        return paymentsRepository.getById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public ResultPage<Payment> getByStatus(PaymentStatus status, Page page) {
        return paymentsRepository.getByStatus(status, page);
    }

}
