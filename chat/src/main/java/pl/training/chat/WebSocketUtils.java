package pl.training.chat;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.List;
import java.util.Map;

public class WebSocketUtils {

    private static final String SIMP_SESSION_ID_HEADER = "simpSessionId";
    private static final String SIMP_CONNECT_MESSAGE_HEADER = "simpConnectMessage";
    private static final String NATIVE_HEADERS_HEADER = "nativeHeaders";

    public String getSessionId(AbstractSubProtocolEvent event) {
        return getHeaders(event).get(SIMP_SESSION_ID_HEADER).toString();
    }

    public MessageHeaders getHeaders(AbstractSubProtocolEvent event) {
        return event.getMessage().getHeaders();
    }

    public Map<String, List<String>> getNativeHeaders(AbstractSubProtocolEvent event) {
        var simpConnectMessage = (GenericMessage) getHeaders(event).get(SIMP_CONNECT_MESSAGE_HEADER);
        return (Map<String, List<String>>) simpConnectMessage.getHeaders().get(NATIVE_HEADERS_HEADER);
    }

    public String getNativeHeader(AbstractSubProtocolEvent event, String key) {
        return getNativeHeaders(event).get(key).get(0);
    }

}
