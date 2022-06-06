package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.training.payments.ports.Payment;
import pl.training.payments.ports.PaymentRequest;
import pl.training.payments.ports.ProcessPaymentUseCase;

@Transactional
@RequiredArgsConstructor
public class TransactionalProcessPaymentUseCase implements ProcessPaymentUseCase {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        return processPaymentUseCase.process(paymentRequest);
    }

}
