package pl.training.payments.broker;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExchangeRatesRepository extends ReactiveMongoRepository<ExchangeLogEntryDocument, String> {
}
