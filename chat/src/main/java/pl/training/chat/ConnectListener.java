package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.List;
import java.util.Map;

@Component
@Log
@RequiredArgsConstructor
public class ConnectListener implements ApplicationListener<SessionConnectedEvent> {

    private static final String SIMP_SESSION_ID_HEADER = "simpSessionId";
    private static final String SIMP_CONNECT_MESSAGE_HEADER = "simpConnectMessage";
    private static final String NATIVE_HEADERS_HEADER = "nativeHeaders";
    private static final String USER_NATIVE_HEADER = "user";

    private final SimpMessagingTemplate messagingTemplate;
    private final TimeProvider timeProvider;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        var headers = event.getMessage().getHeaders();
        var simpSessionId = headers.get(SIMP_SESSION_ID_HEADER).toString();

        var simpConnectMessage = (GenericMessage) headers.get(SIMP_CONNECT_MESSAGE_HEADER);
        var nativeHeaders= (Map<String, List<String>>) simpConnectMessage.getHeaders().get(NATIVE_HEADERS_HEADER);

        var user = nativeHeaders.get(USER_NATIVE_HEADER).stream().findFirst();

        messagingTemplate.convertAndSend("/main-room", new MessageEvent("System", "all", "User " + user + " is now connected to chat", timeProvider.getTimestamp()));
    }

}
