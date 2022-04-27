package pl.training.shop.payments.adapters.web;

import lombok.Data;
import pl.training.shop.commons.money.Money;

@Data
public class PaymentRequestViewModel {

    @Money
    private String value;

}
