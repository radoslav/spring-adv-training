package pl.training.payments.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.training.payments.ports.output.PaymentsWriter;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PaymentsWriterAdapter {

    private final PaymentsWriter paymentsWriter;
    private final PaymentDomainMapper mapper;

    PaymentDomain save(PaymentDomain paymentDomain) {
        var payment = mapper.toPorts(paymentDomain);
        return mapper.toDomain(paymentsWriter.save(payment));
    }

}
