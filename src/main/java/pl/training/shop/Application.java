package pl.training.shop;

import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import pl.training.shop.payments.PaymentRequest;
import pl.training.shop.payments.PaymentService;

import static pl.training.shop.payments.Money.DEFAULT_CURRENCY_UNIT;

@Log
public class Application {

    public static void main(String[] args) {
        var paymentService = new PaymentService();
        var paymentRequest = new PaymentRequest(1L, FastMoney.of(1_000, DEFAULT_CURRENCY_UNIT));
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }

}
