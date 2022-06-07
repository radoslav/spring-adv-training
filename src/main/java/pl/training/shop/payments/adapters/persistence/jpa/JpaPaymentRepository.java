package pl.training.shop.payments.adapters.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;
import static pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity.WITH_PROPERTIES;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String>, JpaPaymentRepositoryExtensions, JpaSpecificationExecutor<PaymentEntity> {

    Page<PaymentEntity> getByStatus(String status, Pageable pageable);

    @Lock(PESSIMISTIC_WRITE)
    @Query("select p from Payment p where p.status = 'COMPLETED' and p.value >= :value")
    List<PaymentEntity> getCompletedWithValue(BigDecimal value);

    @Query("select new pl.training.shop.payments.adapters.persistence.jpa.PaymentEntityView(p.id, p.status) from Payment p where p.id = :id")
    Optional<PaymentEntityView> getPaymentViewById(String id);

    @Query("select p.id as id, p.status as status from Payment p where p.id = :id")
    Optional<PaymentEntityProjection> getPaymentProjectionById(String id);

    // @EntityGraph(value = WITH_PROPERTIES, type = LOAD)
    @EntityGraph(attributePaths = { "properties" })
    @Query("select p from Payment p where p.id = :id")
    Optional<PaymentEntity> byId(String id);

    @Query("select p from Payment p")
    Stream<PaymentEntity> getAll();

    @Async
    @Query("select p from Payment p")
    Future<List<PaymentEntity>> getAllAsync();

}
