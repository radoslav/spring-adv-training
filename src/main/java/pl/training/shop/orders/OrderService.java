package pl.training.shop.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.training.shop.payments.PaymentRequest;
import pl.training.shop.payments.PaymentService;

@Log
@RequiredArgsConstructor
public class OrderService {

    private final PaymentService paymentService;

    public void place(Order order) {
        var paymentValue = order.getTotalValue();
        var paymentRequest = new PaymentRequest(1L, paymentValue);
        var payment = paymentService.process(paymentRequest);
        log.info("New order " + order + " with payment " + payment);
    }

}
