package pl.training.payments.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.training.payments.ports.input.GetPaymentUseCase;
import pl.training.payments.ports.model.Page;
import pl.training.payments.ports.model.Payment;
import pl.training.payments.ports.model.PaymentStatus;
import pl.training.payments.ports.model.ResultPage;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetPaymentUseCaseAdapter implements GetPaymentUseCase {

    private final GetPaymentService getPaymentService;
    private final PaymentDomainMapper mapper;

    @Override
    public Payment getById(String id) {
        var paymentDomain = getPaymentService.getById(id);
        return mapper.toPorts(paymentDomain);
    }

    @Override
    public ResultPage<Payment> getByStatus(PaymentStatus status, Page page) {
        var paymentStatusDomain = mapper.toDomain(status);
        var paymentDomainResultPage = getPaymentService.getByStatus(paymentStatusDomain, page);
        return mapper.toPorts(paymentDomainResultPage);
    }

}
