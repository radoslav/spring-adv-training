package pl.training.shop.payments;

import pl.training.shop.payments.ports.TimeProvider;

import javax.ejb.Stateless;
import java.time.Instant;

@Stateless
public class SystemTimeProvider implements TimeProvider {

    @Override
    public Instant getTimestamp() {
        return Instant.now();
    }

}
