package pl.training.payments.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.training.payments.ports.model.Page;
import pl.training.payments.ports.model.ResultPage;
import pl.training.payments.ports.output.PaymentsReader;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PaymentsReaderAdapter {

    private final PaymentsReader paymentsReader;
    private final PaymentDomainMapper mapper;

    Optional<PaymentDomain> getById(String id) {
        return paymentsReader.getById(id)
                .map(mapper::toDomain);
    }

    ResultPage<PaymentDomain> getByStatus(PaymentStatusDomain statusDomain, Page page) {
        var status = mapper.toPorts(statusDomain);
        var resultPage = paymentsReader.getByStatus(status, page);
        return mapper.toDomain(resultPage);
    }

}
