package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
@Log
@RequiredArgsConstructor
public class ConnectListener implements ApplicationListener<SessionConnectedEvent> {

    private static final String USER_NATIVE_HEADER = "user";

    private final SimpMessagingTemplate messagingTemplate;
    private final TimeProvider timeProvider;
    private final UsersRepository usersRepository;
    private final WebSocketUtils socketUtils = new WebSocketUtils();

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        var sessionId = socketUtils.getSessionId(event);
        var user = socketUtils.getNativeHeader(event, USER_NATIVE_HEADER);
        usersRepository.save(user, sessionId);
        var messageText = "User %s is now connected to chat".formatted(user);
        var message = new MessageEvent("System", "all", messageText, timeProvider.getTimestamp());
        messagingTemplate.convertAndSend("/main-room", message);
    }

}
