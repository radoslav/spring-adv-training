package pl.training.shop.payments.adapters.kafka;

import org.mapstruct.Mapper;
import pl.training.shop.commons.money.FastMoneyMapper;
import pl.training.shop.payments.ports.Payment;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
public interface KafkaPaymentMapper {

    PaymentDto toDto(Payment payment);

    Payment toDomain(PaymentDto paymentDto);

}
