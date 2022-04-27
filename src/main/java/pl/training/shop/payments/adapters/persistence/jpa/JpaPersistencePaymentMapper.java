package pl.training.shop.payments.adapters.persistence.jpa;

import org.javamoney.moneta.FastMoney;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.shop.payments.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", imports = {BigDecimal.class, FastMoney.class})
public interface JpaPersistencePaymentMapper {

    @Mapping(target = "value", expression = "java(BigDecimal.valueOf(payment.getValue().getNumber().doubleValueExact()))")
    @Mapping(target = "currency", expression = "java(payment.getValue().getCurrency().getCurrencyCode())")
    PaymentEntity toEntity(Payment payment);

    @Mapping(target = "value", expression = "java(FastMoney.of(entity.getValue(), entity.getCurrency()))")
    Payment toDomain(PaymentEntity entity);


    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toDomain(List<PaymentEntity> entities);

}
