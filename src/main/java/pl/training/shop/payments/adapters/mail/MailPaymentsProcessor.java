package pl.training.shop.payments.adapters.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.training.shop.commons.mail.MailMessage;
import pl.training.shop.commons.mail.MailService;
import pl.training.shop.commons.mail.TemplateService;
import pl.training.shop.payments.ports.Payment;

import java.util.List;
import java.util.Map;

@Aspect
@Component
@Log
@RequiredArgsConstructor
public class MailPaymentsProcessor {

    private final TemplateService templateService;
    private final MailService mailService;

    @Value("${mail.sender}")
    private String sender;

    @AfterReturning(value = "execution(pl.training.shop.payments.ports.Payment pl.training..*.PaymentProcessor.proc*(..))", returning = "payment")
    public void onPayment(Payment payment) {
        Map<String, Object> data = Map.of("value", payment.getValue().toString());
        var mailText = templateService.evaluate("PaymentNotification", data, "pl");
        var mailMessage = new MailMessage(sender, List.of("client@training.pl"), "Payment notification", mailText);
        mailService.send(mailMessage);
    }

}
