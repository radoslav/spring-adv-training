package pl.training.payments.ports.output;

import pl.training.payments.ports.model.Payment;

public interface PaymentsWriter {

    Payment save(Payment payment);

}
