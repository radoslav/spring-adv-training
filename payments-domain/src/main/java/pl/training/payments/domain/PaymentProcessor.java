package pl.training.payments.domain;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import pl.training.payments.ports.input.GetPaymentUseCase;
import pl.training.payments.ports.input.ProcessPaymentUseCase;
import pl.training.shop.commons.Page;
import pl.training.shop.commons.ResultPage;
import pl.training.shop.payments.ports.PaymentRepository;
import pl.training.shop.payments.ports.PaymentService;
import pl.training.shop.payments.ports.TimeProvider;

@RequiredArgsConstructor
public class PaymentProcessor {

    private static final PaymentStatusDomain DEFAULT_PAYMENT_STATUS = PaymentStatusDomain.CONFIRMED;

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    private final TimeProvider timeProvider;

    @Override
    public PaymentDomain process(PaymentRequestDomain paymentRequest) {
        var paymentValue = calculatePaymentValue(paymentRequest.getValue());
        var payment = createPayment(paymentValue);
        return paymentsRepository.save(payment);
    }

    private PaymentDomain createPayment(FastMoney paymentValue) {
        return PaymentDomain.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentValue)
                .timestamp(timeProvider.getTimestamp())
                .status(DEFAULT_PAYMENT_STATUS)
                .build();
    }

    private FastMoney calculatePaymentValue(FastMoney paymentValue) {
        return paymentValue.add(paymentFeeCalculator.calculateFee(paymentValue));
    }



}
