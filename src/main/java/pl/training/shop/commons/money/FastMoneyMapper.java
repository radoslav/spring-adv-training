package pl.training.shop.commons.money;

import org.javamoney.moneta.FastMoney;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FastMoneyMapper {

    default String toText(FastMoney money) {
        return money != null ? money.toString() : "";
    }

    default FastMoney toMoney(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        return FastMoney.parse(value);
    }

}
