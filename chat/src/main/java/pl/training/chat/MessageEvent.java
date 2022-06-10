package pl.training.chat;

import lombok.Value;
import lombok.With;

@Value
public class MessageEvent {

    String sender;
    String recipient;
    String text;
    @With String timestamp;

}
