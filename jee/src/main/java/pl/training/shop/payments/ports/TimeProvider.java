package pl.training.shop.payments.ports;

import javax.ejb.Remote;
import java.time.Instant;

@Remote
public interface TimeProvider {

    Instant getTimestamp();

}
