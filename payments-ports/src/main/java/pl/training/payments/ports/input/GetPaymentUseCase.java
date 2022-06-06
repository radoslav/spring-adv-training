package pl.training.payments.ports.input;

import pl.training.payments.ports.model.Page;
import pl.training.payments.ports.model.Payment;
import pl.training.payments.ports.model.PaymentStatus;
import pl.training.payments.ports.model.ResultPage;

public interface GetPaymentUseCase {

    Payment getById(String id);

    ResultPage<Payment> getByStatus(PaymentStatus status, Page page);

}
