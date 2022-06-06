package pl.training.payments.ports.output;

import java.time.Instant;

public interface TimeProvider {

    Instant getTimestamp();

}
