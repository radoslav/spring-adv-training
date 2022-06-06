package pl.training.shop.payments.ports;

import pl.training.shop.commons.Page;
import pl.training.shop.commons.ResultPage;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> getById(String id);

    ResultPage<Payment> getByStatus(PaymentStatus status, Page page);

}
