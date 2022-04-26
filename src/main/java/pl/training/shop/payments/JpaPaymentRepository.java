package pl.training.shop.payments;

import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Transactional
public class JpaPaymentRepository implements PaymentRepository {

    @PersistenceContext
    @Setter
    private EntityManager entityManager;

    @Override
    public Payment save(Payment payment) {
        return payment;
    }

    @Override
    public Optional<Payment> getById(String id) {
        return Optional.empty();
    }

}
