package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Log
@RequiredArgsConstructor
public class ChatController {

    private static final String ALL_RECIPIENTS = "all";

    private final SimpMessagingTemplate messagingTemplate;
    private final TimeProvider timeProvider;
    private final UsersRepository usersRepository;

    @MessageMapping("/chat")
    public void onMessage(@Payload MessageEvent messageEvent, @Header("simpSessionId") String sessionId) {
        var timestamp = timeProvider.getTimestamp();
        var message = messageEvent.withTimestamp(timestamp);
        log.info("New message: " + message);
        if (messageEvent.getRecipient().equals(ALL_RECIPIENTS)) {
            messagingTemplate.convertAndSend("/main-room", message);
        } else {
            messagingTemplate.convertAndSend("/private-rooms/" + message.getRecipient(), message);
            messagingTemplate.convertAndSend("/private-rooms/" + message.getSender(), message);
        }

    }

    /*@MessageMapping("/chat")
    @SendTo("/main-room")
    public MessageEvent onMessage(MessageEvent messageEvent) {
        var timestamp = timeProvider.getTimestamp();
        var message = messageEvent.withTimestamp(timestamp);
        log.info("New message: " + message);
        return message;
    }*/

}
