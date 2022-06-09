package pl.training.shop.payments.adapters.jms;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Training"),
})
@Log
public class PaymentProcessor implements MessageListener {

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        var paymentDto = message.getBody(PaymentDto.class);
        log.info("New message: " + paymentDto);
    }

}
