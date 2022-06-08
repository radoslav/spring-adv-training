package pl.training.payments.broker;

import lombok.Value;
import lombok.With;

@Value
public class PaymentDomain {

    String id;
    String value;
    @With String status;

}
