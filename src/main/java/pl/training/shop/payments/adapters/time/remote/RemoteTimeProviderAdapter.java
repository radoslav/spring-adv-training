package pl.training.shop.payments.adapters.time.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.training.shop.payments.ports.ServiceUnavailableException;
import pl.training.shop.payments.ports.TimeProvider;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RemoteTimeProviderAdapter implements TimeProvider {

    private final RemoteTimeProvider timeProvider;
    private final RemoteTimeRestMapper mapper;

    @Override
    public Instant getTimestamp() {
        return timeProvider.getTime()
                .map(mapper::toDomain)
                .orElseThrow(ServiceUnavailableException::new);
    }

}
