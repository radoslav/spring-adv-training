package pl.training.payments.broker;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentsRepository extends ReactiveMongoRepository<PaymentDocument, String> {
}
