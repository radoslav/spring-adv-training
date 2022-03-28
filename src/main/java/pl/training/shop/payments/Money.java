package pl.training.shop.payments;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Locale;

public class Money {

    private static final Locale DEFAULT_LOCALE = new Locale("pl", "PL");
    public  static final CurrencyUnit DEFAULT_CURRENCY_UNIT = Monetary.getCurrency(DEFAULT_LOCALE);

    private Money() {
    }

}
