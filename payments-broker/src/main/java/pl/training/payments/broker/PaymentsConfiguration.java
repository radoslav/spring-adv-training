package pl.training.payments.broker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class PaymentsConfiguration {

    private static final String PAYMENTS = "payments";
    private static final String LATEST_PAYMENT = "%s/latest".formatted(PAYMENTS);
    private static final String EXCHANGE_RATES = "exchange-rates";

    @Bean
    public RouterFunction<ServerResponse> routes(PaymentRestAdapter paymentRestAdapter) {
        return RouterFunctions
                .route(GET(PAYMENTS), paymentRestAdapter::getPayments)
                .andRoute(POST(PAYMENTS), paymentRestAdapter::process)
                .andRoute(GET(LATEST_PAYMENT), paymentRestAdapter::getLatestPayment)
                .andRoute(GET(EXCHANGE_RATES), paymentRestAdapter::getExchangeRates);
    }

}
