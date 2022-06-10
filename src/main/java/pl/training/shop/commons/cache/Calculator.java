package pl.training.shop.commons.cache;

import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Log
@CacheConfig(cacheNames = "calculator", keyGenerator = "stringKeyGenerator")
public class Calculator {

    @Cacheable(condition = "#a < #b")
    public int add(int a, int b) {
        log.info("Calculating sum %d and %d".formatted(a, b));
        return a + b;
    }

    @CacheEvict(/*, allEntries = true*/)
    public void reset(String key) {
        log.info("Clearing cache...");
    }

    @CachePut
    public int preCalculate(int a, int b) {
        return a + b;
    }

}
