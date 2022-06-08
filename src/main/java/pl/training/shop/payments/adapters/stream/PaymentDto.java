package pl.training.shop.payments.adapters.stream;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDto {

    private String id;
    private String value;
    private String status;
    private Instant timestamp;

}
