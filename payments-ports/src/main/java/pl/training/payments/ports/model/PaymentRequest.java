package pl.training.payments.ports.model;

import lombok.Value;

@Value
public class PaymentRequest {

    Long id;
    String value;

}
