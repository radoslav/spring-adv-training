package pl.training.shop.payments.adapters.jms;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class PaymentDto implements Serializable {

    private String id;
    private String value;
    private String status;
    private Instant timestamp;

}
