package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.ports.PaymentRepository;

import java.util.Optional;

@Transactional
@Component
@RequiredArgsConstructor
public class JpaPaymentRepositoryAdapter implements PaymentRepository {

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
        return paymentRepository.getById(id)
                .map(paymentMapper::toDomain);
    }

}
