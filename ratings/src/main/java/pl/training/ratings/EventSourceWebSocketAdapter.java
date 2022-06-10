package pl.training.ratings;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Log
@RequiredArgsConstructor
public class EventSourceWebSocketAdapter implements WebSocketHandler {

    private final EventSource eventSource;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var output = eventSource.getExchangeRates()
                .map(BigDecimal::toString)
                .map(session::textMessage);
        return session.send(output).and(session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(this::onMessage));
    }

    private void onMessage(String message) {
        log.info("New message: " + message);
    }

}
