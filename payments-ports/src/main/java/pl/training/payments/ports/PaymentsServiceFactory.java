package pl.training.payments.ports;

import pl.training.payments.ports.input.GetPaymentUseCase;
import pl.training.payments.ports.input.ProcessPaymentUseCase;
import pl.training.payments.ports.output.PaymentsReader;
import pl.training.payments.ports.output.PaymentsWriter;
import pl.training.payments.ports.output.TimeProvider;

public interface PaymentsServiceFactory {

    GetPaymentUseCase create(PaymentsReader paymentsReader);

    ProcessPaymentUseCase create(PaymentsWriter paymentsWriter, TimeProvider timeProvider);

}
