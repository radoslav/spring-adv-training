package pl.training.shop.payments.adapters.web;

import org.mapstruct.Mapper;
import pl.training.shop.commons.money.FastMoneyMapper;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentRequest;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
public interface WebPaymentMapper {

    PaymentRequest toDomain(PaymentRequestViewModel viewModel);

    PaymentViewModel toViewModel(Payment payment);

}
