package pl.training.payments.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.training.payments.ports.input.ProcessPaymentUseCase;
import pl.training.payments.ports.model.Payment;
import pl.training.payments.ports.model.PaymentRequest;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProcessPaymentUseCaseAdapter implements ProcessPaymentUseCase {

    private final ProcessPaymentService processPaymentService;
    private final PaymentDomainMapper mapper;

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var paymentRequestDomain = mapper.toDomain(paymentRequest);
        var paymentDomain = processPaymentService.process(paymentRequestDomain);
        return mapper.toPorts(paymentDomain);
    }

}
