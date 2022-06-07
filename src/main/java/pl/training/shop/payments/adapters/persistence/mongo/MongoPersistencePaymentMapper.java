package pl.training.shop.payments.adapters.persistence.mongo;

import org.javamoney.moneta.FastMoney;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.shop.payments.ports.Payment;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", imports = {BigDecimal.class, FastMoney.class})
public interface MongoPersistencePaymentMapper {

    @Mapping(target = "value", expression = "java(BigDecimal.valueOf(payment.getValue().getNumber().doubleValueExact()))")
    @Mapping(target = "currency", expression = "java(payment.getValue().getCurrency().getCurrencyCode())")
    PaymentDocument toDocument(Payment payment);

    @Mapping(target = "value", expression = "java(FastMoney.of(document.getValue(), document.getCurrency()))")
    Payment toDomain(PaymentDocument document);

    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toDomain(List<PaymentDocument> documents);

}
