package pl.training.shop.payments.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.training.shop.payments.ports.PaymentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.training.shop.payments.domain.PaymentFixture.*;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorTest {

    private PaymentProcessor paymentProcessor;
    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void beforeEach() {
        when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());
        paymentProcessor = new PaymentProcessor(() -> TEST_ID, TEST_PAYMENT_FEE_CALCULATOR, paymentRepository, () -> TEST_TIME);
    }

    @Test
    void given_a_payment_request_when_process_then_returns_a_payment() {
        var payment = paymentProcessor.process(new PaymentRequest(1L, TEST_VALUE));
        assertEquals(TEST_PAYMENT, payment);
    }

    @Disabled("Move to integration tests")
    @Test
    void given_a_payment_request_when_process_then_the_payment_is_persisted() {
        var payment = paymentProcessor.process(new PaymentRequest(1L, TEST_VALUE));
        verify(paymentRepository).save(payment);
    }

}
