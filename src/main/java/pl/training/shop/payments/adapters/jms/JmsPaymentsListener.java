package pl.training.shop.payments.adapters.jms;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;

@Service
@Log
public class JmsPaymentsListener {

    @JmsListener(destination = "Training") //, containerFactory = "trainingTopicContainerFactory")
    @SneakyThrows
    public void onMessage(Message message) {
        var paymentDto = message.getBody(PaymentDto.class);
        log.info("New message: " + paymentDto);
    }

}
