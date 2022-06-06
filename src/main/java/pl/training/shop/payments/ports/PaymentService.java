package pl.training.shop.payments.ports;

import pl.training.shop.commons.Page;
import pl.training.shop.commons.ResultPage;

public interface PaymentService {

    Payment process(PaymentRequest paymentRequest);

    Payment getById(String id);

    ResultPage<Payment> getByStatus(PaymentStatus status, Page page);

}
