package pl.training.shop.payments.adapters.kafka;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log
public class KafkaPaymentsListener {

    @KafkaListener(topics = "main", groupId = "training")
    public void onMessage(String message) {
        log.info("Kafka message: " + message);
    }

}
