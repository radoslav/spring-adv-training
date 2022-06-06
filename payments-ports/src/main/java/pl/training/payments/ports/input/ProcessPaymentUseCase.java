package pl.training.payments.ports.input;

import pl.training.payments.ports.model.Payment;
import pl.training.payments.ports.model.PaymentRequest;

public interface ProcessPaymentUseCase {

    Payment process(PaymentRequest paymentRequest);

}
