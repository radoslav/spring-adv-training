package pl.training.shop.payments.adapters.persistence.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static pl.training.shop.payments.PaymentFixture.PAYMENT_STATUS;
import static pl.training.shop.payments.PaymentFixture.createPaymentEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class JpaPaymentRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JpaPaymentRepository paymentRepository;

    @BeforeEach
    void beforeEach() {
        entityManager.persist(createPaymentEntity());
        entityManager.flush();
    }

    @Test
    void given_confirmed_payment_in_database_when_get_by_status_then_returns_the_payment() {
        var result = paymentRepository.getByStatus(PAYMENT_STATUS.name(), PageRequest.of(0, 1));
        assertFalse(result.getContent().isEmpty());
    }

}
