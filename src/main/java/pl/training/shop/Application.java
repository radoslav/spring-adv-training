package pl.training.shop;

import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import pl.training.shop.payments.PaymentRequest;
import pl.training.shop.payments.PaymentProcessor;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Locale;

@Log
public class Application {

    private static final Locale DEFAULT_LOCALE = new Locale("pl", "PL");
    private static final CurrencyUnit DEFAULT_CURRENCY_UNIT = Monetary.getCurrency(DEFAULT_LOCALE);

    public static void main(String[] args) {
        var paymentService = new PaymentProcessor();
        var paymentRequest = new PaymentRequest(1L, FastMoney.of(1_000, DEFAULT_CURRENCY_UNIT));
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }

}
