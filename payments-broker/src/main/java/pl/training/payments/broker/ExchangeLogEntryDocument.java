package pl.training.payments.broker;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class ExchangeLogEntryDocument {

    @Id
    String id = UUID.randomUUID().toString();
    BigDecimal exchangeRate;
    BigDecimal value;

    public BigDecimal getExchangeValue() {
        return value.multiply(exchangeRate);
    }

}
