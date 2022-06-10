package pl.training.shop.commons.cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@EnableCaching
@Configuration
public class CacheConfiguration {

    /*@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("calculator");
    }*/

    /*@Bean
    public CacheManager cacheManager() {
        // var config = new ClientConfig();
        // var hazelcastInstance = HazelcastClient.newHazelcastClient(config);

        var config = new Config();
        var hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        return new HazelcastCacheManager(hazelcastInstance);
    }*/

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        var config = RedisCacheConfiguration.defaultCacheConfig();
        config.usePrefix();
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

}
