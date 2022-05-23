package pl.training.payments.adapters.persistence.jpa;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.payments.ports.Payment;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", imports = BigDecimal.class)
public interface JpaPersistencePaymentMapper {

    @Mapping(target = "value", expression = "java(new BigDecimal(payment.getValue().split(\" \")[1]))")
    @Mapping(target = "currency", expression = "java(payment.getValue().split(\" \")[0])")
    PaymentEntity toEntity(Payment payment);

    @Mapping(target = "value", expression = "java(entity.getValue() + \" \" + entity.getCurrency())")
    Payment toDomain(PaymentEntity entity);


    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toDomain(List<PaymentEntity> entities);

}
