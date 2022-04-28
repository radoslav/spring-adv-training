package pl.training.shop.payments.domain;

import org.javamoney.moneta.FastMoney;
import org.mockito.stubbing.Answer;
import pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity;
import pl.training.shop.payments.adapters.rest.PaymentDto;

import java.math.BigDecimal;
import java.time.Instant;

import static pl.training.shop.payments.domain.PaymentStatus.CONFIRMED;

public class PaymentFixture {

    public static final String TEST_ID = "1";
    public static final Instant TEST_TIME = Instant.now();
    public static final FastMoney TEST_VALUE = FastMoney.of(1_000, "PLN");
    public static final PaymentFeeCalculator TEST_PAYMENT_FEE_CALCULATOR = (value) -> FastMoney.of(10, "PLN");
    public static final FastMoney TEST_PAYMENT_VALUE = TEST_VALUE.add(TEST_PAYMENT_FEE_CALCULATOR.calculateFee(TEST_VALUE));
    public static final Payment TEST_PAYMENT = Payment.builder()
            .id(TEST_ID)
            .value(TEST_PAYMENT_VALUE)
            .status(CONFIRMED)
            .timestamp(TEST_TIME)
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
        entity.setId(TEST_ID);
        entity.setValue(BigDecimal.valueOf(TEST_PAYMENT_VALUE.getNumber().doubleValueExact()));
        entity.setCurrency(TEST_PAYMENT_VALUE.getCurrency().getCurrencyCode());
        entity.setTimestamp(TEST_TIME);
        entity.setStatus(CONFIRMED.name());
        return entity;
    }

}
