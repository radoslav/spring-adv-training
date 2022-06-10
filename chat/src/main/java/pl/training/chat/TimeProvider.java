package pl.training.chat;

import java.time.Instant;

public interface TimeProvider {

    Instant getTimestamp();

}
