package pl.training.shop.orders;

import lombok.Value;
import org.javamoney.moneta.FastMoney;

import java.util.List;

@Value
public class Order {

    private static final String DEFAULT_CURRENCY_CODE = "PLN";

    Long id;
    List<OrderEntry> orderEntries;

    FastMoney getTotalValue() {
        return orderEntries.stream()
                .map(entry -> entry.getPrice().multiply(entry.getQuantity()))
                .reduce(FastMoney.of(0, DEFAULT_CURRENCY_CODE), FastMoney::add);
    }

}
