package pl.training.payments.ports.output;

import pl.training.payments.ports.model.Page;
import pl.training.payments.ports.model.Payment;
import pl.training.payments.ports.model.PaymentStatus;
import pl.training.payments.ports.model.ResultPage;

import java.util.Optional;

public interface PaymentsReader {

    Optional<Payment> getById(String id);

    ResultPage<Payment> getByStatus(PaymentStatus status, Page page);

}
