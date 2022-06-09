package pl.training.shop;

import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.training.shop.commons.web.RestTemplateAuthorizationInterceptor;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

@EnableFeignClients
@Configuration
public class ShopConfiguration implements WebMvcConfigurer {

    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8080";
    private static final String CLIENT_EJB_CONTEXT = "jboss.naming.client.ejb.context";

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

    @Bean
    public Properties jndiProperties() {
        var properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        properties.put(Context.PROVIDER_URL, PROVIDER_URL);
        properties.put(CLIENT_EJB_CONTEXT, this);
        return properties;
    }

    @Bean
    public JndiTemplate jndiTemplate(Properties jndiProperties) {
        return new JndiTemplate(jndiProperties);
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory(JndiTemplate jndiTemplate) throws NamingException {
        return jndiTemplate.lookup("jms/RemoteConnectionFactory", ConnectionFactory.class);
    }

    @Bean
    public Topic trainingTopic(JndiTemplate jndiTemplate) throws NamingException {
        return jndiTemplate.lookup("jms/topic/Training", Topic.class);
    }

    @Bean
    public Queue mailQueue(JndiTemplate jndiTemplate) throws NamingException {
        return jndiTemplate.lookup("jms/queue/Mail", Queue.class);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        var cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        return new JmsTemplate(cachingConnectionFactory);
    }

}
