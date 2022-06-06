package pl.training.payments.domain;

import pl.training.payments.ports.PaymentsServiceFactory;
import pl.training.payments.ports.input.GetPaymentUseCase;
import pl.training.payments.ports.input.ProcessPaymentUseCase;
import pl.training.payments.ports.output.PaymentsReader;
import pl.training.payments.ports.output.PaymentsWriter;
import pl.training.payments.ports.output.TimeProvider;

public class DefaultPaymentServiceFactory implements PaymentsServiceFactory {

    private static final PaymentIdGenerator PAYMENT_ID_GENERATOR = new UuidPaymentIdGenerator();
    private static final PaymentFeeCalculator PAYMENT_FEE_CALCULATOR = new PercentagePaymentFeeCalculator(0.01);

    @Override
    public GetPaymentUseCase create(PaymentsReader paymentsReader) {
        return null;
    }

    @Override
    public ProcessPaymentUseCase create(PaymentsWriter paymentsWriter, TimeProvider timeProvider) {
        return null;
    }

}
