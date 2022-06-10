package pl.training.ratings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
public class RatingsConfiguration {

    @Bean
    public HandlerMapping handlerMapping(EventSourceWebSocketAdapter eventSourceWebSocketAdapter) {
        var handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(Map.of("/events", eventSourceWebSocketAdapter));
        return handlerMapping;
    }

}
