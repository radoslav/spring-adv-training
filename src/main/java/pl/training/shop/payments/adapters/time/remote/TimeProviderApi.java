package pl.training.shop.payments.adapters.time.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "timeProvider", url = "${time-api.endpoint}")
public interface TimeProviderApi {

    @GetMapping("{timezone}")
    TimeDto getTime(@PathVariable String timezone);

}
