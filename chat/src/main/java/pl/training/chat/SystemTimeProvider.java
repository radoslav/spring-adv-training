package pl.training.chat;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SystemTimeProvider implements TimeProvider {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    @Override
    public String getTimestamp() {
        return dateTimeFormatter.format(LocalDateTime.now());
    }

}
