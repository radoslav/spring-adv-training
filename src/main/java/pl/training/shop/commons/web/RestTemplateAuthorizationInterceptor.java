package pl.training.shop.commons.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class RestTemplateAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set(AUTHORIZATION, "Bearer %s".formatted(token));
        return execution.execute(request, body);
    }

}
