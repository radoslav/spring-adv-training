package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.commons.Page;
import pl.training.shop.commons.ResultPage;
import pl.training.shop.payments.adapters.persistence.jpa.JpaPaymentRepository;
import pl.training.shop.payments.adapters.persistence.jpa.JpaPersistencePaymentMapper;
import pl.training.shop.payments.ports.Payment;
import pl.training.shop.payments.ports.PaymentRepository;
import pl.training.shop.payments.ports.PaymentStatus;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Primary
@Transactional(propagation = MANDATORY)
@Component
@RequiredArgsConstructor
public class MongoPaymentRepositoryAdapter implements PaymentRepository {

    private final MongoPaymentRepository mongoPaymentRepository;
    private final MongoPersistencePaymentMapper paymentMapper;

    @Override
    public Payment save(Payment payment) {
        var entity = paymentMapper.toDocument(payment);
        mongoPaymentRepository.save(entity);
        return paymentMapper.toDomain(entity);
    }

    @Override
    public Optional<Payment> getById(String id) {
        return mongoPaymentRepository.findById(id)
                .map(paymentMapper::toDomain);
    }

    @Override
    public ResultPage<Payment> getByStatus(PaymentStatus status, Page page) {
        var pageRequest = PageRequest.of(page.getNumber(), page.getSize());
        var resultPage= mongoPaymentRepository.getByStatus(status.name(), pageRequest);
        var data = paymentMapper.toDomain(resultPage.getContent());
        return new ResultPage<>(data, page.getNumber(), resultPage.getTotalPages());
    }

}
