package pl.training.payments.broker;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentsMapper {

    PaymentDocument toDocument(PaymentDomain paymentDomain);

    PaymentDomain toDomain(PaymentDocument paymentDocument);

    PaymentDto toDto(PaymentDomain paymentDomain);

    PaymentDomain toDomain(PaymentDto paymentDto);

}
