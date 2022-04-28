package pl.training.shop.payments.adapters.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.training.shop.commons.web.RestExceptionResponseBuilder;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.ports.PaymentService;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.training.shop.payments.domain.PaymentFixture.*;

@WebMvcTest(PaymentRestController.class)
@ExtendWith(SpringExtension.class)
class PaymentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private RestPaymentMapper mapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public RestExceptionResponseBuilder restExceptionResponseBuilder(MessageSource messageSource) {
            return new RestExceptionResponseBuilder(messageSource);
        }

    }

    @BeforeEach
    void beforeEach() {
        when(paymentService.getById(TEST_ID)).thenReturn(TEST_PAYMENT);
        when(mapper.toDto(any(Payment.class))).then(MAP_TO_DTO);
    }

    @Test
    void given_payment_when_get_by_id_then_returns_the_payment() throws Exception {
        mockMvc.perform(get("/api/payments/" + TEST_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TEST_ID)))
                .andExpect(jsonPath("$.value", is(TEST_PAYMENT_VALUE.toString())));
    }

}
