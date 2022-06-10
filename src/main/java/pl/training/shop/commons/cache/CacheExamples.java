package pl.training.shop.commons.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log
public class CacheExamples implements ApplicationRunner {

    private final Calculator calculator;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // calculator.preCalculate(1, 3);
        log.info("Sum for 1 and 3  is: " + calculator.add(1, 3));
        log.info("Sum for 1 and 3  is: " + calculator.add(1, 3));
        calculator.reset("pl.training.shop.commons.cache.Calculator1_3");
        log.info("Sum for 1 and 3  is: " + calculator.add(1, 3));

        Optional.ofNullable(cacheManager.getCache("calculator"))
                .ifPresent(cache -> {
                    var value = cache.get("pl.training.shop.commons.cache.Calculator1_3").get();
                    log.info("Value: " + value);
                });
    }

}
