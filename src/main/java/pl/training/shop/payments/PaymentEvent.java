package pl.training.shop.payments;

import lombok.Value;

@Value
public class PaymentEvent {

    Payment payment;
    PaymentEventType type;

    enum PaymentEventType {

        CREATED, FAILED

    }

}
