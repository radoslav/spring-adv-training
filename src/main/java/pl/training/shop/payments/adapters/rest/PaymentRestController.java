package pl.training.shop.payments.adapters.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentRestController {

    @GetMapping("/api/greetings")
    public String sayHello() {
        return "Hello";
    }

}
