package pl.training.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.mockito.stubbing.Answer;
import pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity;
import pl.training.shop.payments.adapters.rest.PaymentDto;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

import static pl.training.shop.payments.domain.PaymentStatus.CONFIRMED;

public class PaymentFixture {

    public static final String PAYMENT_ID = "1";
    public static final Instant PAYMENT_TIMESTAMP = Instant.now();
    public static final FastMoney PAYMENT_REQUEST_VALUE = FastMoney.of(1_000, "PLN");
    public static final FastMoney PAYMENT_FEE_VALUE = FastMoney.of(1, "PLN");
    public static final FastMoney PAYMENT_VALUE = PAYMENT_REQUEST_VALUE.add(PAYMENT_FEE_VALUE);
    public static final PaymentStatus PAYMENT_STATUS = CONFIRMED;
    public static final Payment TEST_PAYMENT = Payment.builder()
            .id(PAYMENT_ID)
            .value(PAYMENT_VALUE)
            .status(PAYMENT_STATUS)
            .timestamp(PAYMENT_TIMESTAMP)
            .build();
    public static final Answer<PaymentDto> MAP_TO_DTO = (invocation) -> {
        var payment = invocation.getArgument(0, Payment.class);
        var dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setValue(payment.getValue().toString());
        return dto;
    };
    public static PaymentEntity createPaymentEntity() {
        var entity = new PaymentEntity();
        entity.setId(PAYMENT_ID);
        entity.setValue(BigDecimal.valueOf(PAYMENT_VALUE.getNumber().doubleValueExact()));
        entity.setCurrency(PAYMENT_VALUE.getCurrency().getCurrencyCode());
        entity.setTimestamp(PAYMENT_TIMESTAMP);
        entity.setStatus(PAYMENT_STATUS.name());
        return entity;
    }

}
