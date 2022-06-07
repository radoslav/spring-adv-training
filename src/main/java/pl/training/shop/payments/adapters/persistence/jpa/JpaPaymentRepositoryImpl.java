package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

import static pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity.GET_WITH_VALUE_GREATER_THAN;

public class JpaPaymentRepositoryImpl implements JpaPaymentRepositoryExtensions {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentEntity> getWithValueGreaterThan(BigDecimal value) {
        return entityManager.createNamedQuery(GET_WITH_VALUE_GREATER_THAN, PaymentEntity.class)
                .setParameter("value", value)
                .getResultList();
    }

}
