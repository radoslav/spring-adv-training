package pl.training.shop.payments.adapters.time.remote;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.training.shop.payments.ports.ServiceUnavailableException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestTemplateTimeProvider {

    private final RestTemplate restTemplate;

    @Value("${time-api.endpoint}/${time-api.timezone}")
    @Setter
    private String timeApiUrl;

    public Optional<Long> getTime() {
        try {
           return Optional.ofNullable(restTemplate.getForObject(timeApiUrl, TimeDto.class))
                   .map(TimeDto::getUnixtime);
        } catch (RestClientException exception) {
            throw new ServiceUnavailableException();
        }
    }

}
