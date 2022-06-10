package pl.training.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class DisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate messagingTemplate;
    private final TimeProvider timeProvider;
    private final UsersRepository usersRepository;
    private final WebSocketUtils socketUtils = new WebSocketUtils();

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        var sessionId = socketUtils.getSessionId(event);
        usersRepository.getUser(sessionId).ifPresent(user -> {
            var messageText = "User %s is now disconnected to chat".formatted(user);
            var message = new MessageEvent("System", "all", messageText, timeProvider.getTimestamp());
            messagingTemplate.convertAndSend("/main-room", message);
        });
    }

}
