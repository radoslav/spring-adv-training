package pl.training.shop.payments.adapters.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.ShopApplication;

import javax.persistence.EntityManager;

import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.training.shop.payments.domain.PaymentFixture.*;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PaymentRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Test
    void given_payment_when_get_by_id_then_returns_the_payment() throws Exception {
        entityManager.persist(createPaymentEntity());
        entityManager.flush();
        mockMvc.perform(get("/api/payments/" + TEST_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TEST_ID)))
                .andExpect(jsonPath("$.value", is(TEST_PAYMENT_VALUE.toString())));
    }

}
