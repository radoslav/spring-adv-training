package pl.training.payments.domain;

import org.mapstruct.factory.Mappers;
import pl.training.payments.ports.PaymentsServiceFactory;
import pl.training.payments.ports.input.GetPaymentUseCase;
import pl.training.payments.ports.input.ProcessPaymentUseCase;
import pl.training.payments.ports.output.PaymentsReader;
import pl.training.payments.ports.output.PaymentsWriter;
import pl.training.payments.ports.output.TimeProvider;

public class DefaultPaymentsServiceFactory implements PaymentsServiceFactory {

    private static final PaymentIdGenerator PAYMENT_ID_GENERATOR = new UuidPaymentIdGenerator();
    private static final PaymentFeeCalculator PAYMENT_FEE_CALCULATOR = new PercentagePaymentFeeCalculator(0.01);
    private static final PaymentDomainMapper MAPPER = Mappers.getMapper(PaymentDomainMapper.class);

    @Override
    public GetPaymentUseCase create(PaymentsReader paymentsReader) {
        var paymentsReaderAdapter = new PaymentsReaderAdapter(paymentsReader, MAPPER);
        var getPaymentService = new GetPaymentService(paymentsReaderAdapter);
        return new GetPaymentUseCaseAdapter(getPaymentService, MAPPER);
    }

    @Override
    public ProcessPaymentUseCase create(PaymentsWriter paymentsWriter, TimeProvider timeProvider) {
        var paymentsWriterAdapter = new PaymentsWriterAdapter(paymentsWriter, MAPPER);
        var processPaymentService = new ProcessPaymentService(PAYMENT_ID_GENERATOR, PAYMENT_FEE_CALCULATOR, paymentsWriterAdapter, timeProvider);
        return new ProcessPaymentUseCaseAdapter(processPaymentService, MAPPER);
    }

}
