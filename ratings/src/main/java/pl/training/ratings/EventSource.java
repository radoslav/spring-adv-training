package pl.training.ratings;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.util.Random;

import static java.time.Duration.ofSeconds;

@Component
public class EventSource {

    private final Random random = new Random();
    private static final double MAX_RATE_FLUCTUATION = 0.3;
    private static final int RATE_UPDATE_INTERVAL_IN_SECONDS = 3;

    public Flux<BigDecimal> getExchangeRates() {
        var interval =  Flux.interval(ofSeconds(RATE_UPDATE_INTERVAL_IN_SECONDS));
        var values = Flux.generate(this::generateNewRate).map(BigDecimal::valueOf);
        return interval.zipWith(values).map(Tuple2::getT2);
    }

    private void generateNewRate(SynchronousSink<Double> sink) {
        sink.next(random.nextDouble(MAX_RATE_FLUCTUATION) * (random.nextBoolean() ? -1 : 1));
    }

}
