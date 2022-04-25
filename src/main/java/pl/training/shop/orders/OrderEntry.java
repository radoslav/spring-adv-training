package pl.training.shop.orders;

import lombok.Value;
import org.javamoney.moneta.FastMoney;

@Value
public class OrderEntry {

    Long productId;
    FastMoney price;
    int quantity;

}
