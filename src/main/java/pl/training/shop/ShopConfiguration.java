package pl.training.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.training.shop.commons.web.RestTemplateAuthorizationInterceptor;

@EnableFeignClients
@Configuration
public class ShopConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("http://localhost:4200");
    }

    @Bean
    public RestTemplate restTemplate(@Value("${time-api.token}") String token) {
        return new RestTemplateBuilder()
                .additionalInterceptors(new RestTemplateAuthorizationInterceptor(token))
                .build();
    }

}
