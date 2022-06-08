package pl.training.payments.broker;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
public class PaymentDocument {

    @Id
    private String id;
    private String value;
    private String status;
    private Instant timestamp;

}
