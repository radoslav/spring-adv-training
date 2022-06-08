package pl.training.shop.payments.adapters.time.remote;

import org.mapstruct.Mapper;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface RemoteTimeRestMapper {

    default Instant toDomain(long seconds) {
        return Instant.ofEpochSecond(seconds);
    }

}
