package pl.training.chat;

import lombok.Value;
import lombok.With;

import java.time.Instant;

@Value
public class Message {

    String sender;
    String recipient;
    String text;
    @With Instant timestamp;

}
