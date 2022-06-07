package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
@Log
public class MongoExamples {

    private final MongoPaymentRepository paymentRepository;

    @Async
    @Scheduled(fixedRate = 10_000)
    public void run() {
        var result = paymentRepository.findAll();
        result.forEach(document -> log.info(document.toString()));
    }

}
