package pl.training.payments.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.training.payments.ports.model.Page;
import pl.training.payments.ports.model.PaymentNotFoundException;
import pl.training.payments.ports.model.ResultPage;
import pl.training.payments.ports.output.PaymentsReader;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GetPaymentService {

    private PaymentsReader paymentsReader;

    PaymentDomain getById(String id) {
        return paymentsReader.getById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    ResultPage<PaymentDomain> getByStatus(PaymentStatusDomain status, Page page) {
        return paymentsReader.getByStatus(status, page);
    }

}
