package pl.training.payments.broker;

import lombok.Value;
import lombok.With;

import java.time.Instant;

@Value
public class PaymentDomain {

    String id;
    String value;
    @With String status;
    Instant timestamp;

}
