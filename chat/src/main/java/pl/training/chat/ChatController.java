package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Log
@RequiredArgsConstructor
public class ChatController {

    private final TimeProvider timeProvider;

    @MessageMapping("/chat")
    @SendTo("/main-room")
    public Message onMessage(Message message) {
        var timestamp = timeProvider.getTimestamp();
        var response = message.withTimestamp(timestamp);
        log.info("New message: " + response);
        return response;
    }

}
