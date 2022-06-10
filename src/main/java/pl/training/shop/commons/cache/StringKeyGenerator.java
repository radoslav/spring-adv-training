package pl.training.shop.commons.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

@Component
public class StringKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getName() + Arrays.stream(params)
                .map(Object::toString)
                .collect(joining("_"));
    }

}
