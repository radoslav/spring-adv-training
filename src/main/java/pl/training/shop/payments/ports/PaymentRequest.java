package pl.training.shop.payments.ports;

import lombok.Value;
import org.javamoney.moneta.FastMoney;

@Value
public class PaymentRequest {

    Long id;
    FastMoney value;

}
