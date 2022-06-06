package pl.training.payments.adapters.persistence.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.training.payments.ports.model.Page;
import pl.training.payments.ports.model.Payment;
import pl.training.payments.ports.model.PaymentStatus;
import pl.training.payments.ports.model.ResultPage;
import pl.training.payments.ports.output.PaymentsReader;
import pl.training.payments.ports.output.PaymentsWriter;

import java.util.Optional;

@Transactional // (propagation = Propagation.MANDATORY)
@Component
@RequiredArgsConstructor
public class JpaPaymentRepositoryAdapter implements PaymentsReader, PaymentsWriter {

    private final JpaPaymentRepository paymentRepository;
    private final JpaPersistencePaymentMapper paymentMapper;

    @Override
    public Payment save(Payment payment) {
        var entity = paymentMapper.toEntity(payment);
        paymentRepository.save(entity);
        return paymentMapper.toDomain(entity);
    }

    @Override
    public Optional<Payment> getById(String id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDomain);
    }

    @Override
    public ResultPage<Payment> getByStatus(PaymentStatus status, Page page) {
        var pageRequest = PageRequest.of(page.getNumber(), page.getSize());
        var resultPage= paymentRepository.getByStatus(status.name(), pageRequest);
        var data = paymentMapper.toDomain(resultPage.getContent());
        return new ResultPage<>(data, page.getNumber(), resultPage.getTotalPages());
    }

}
