package uk.ed.ac.uk.gateway.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Value("${RABBITMQ_HOST:host.docker.internal}")
    private String rabbitMQHost;

    @Value("${RABBITMQ_PORT:5672}")
    private int rabbitMQPort;

    @Value("${RABBITMQ_USERNAME:guest}")
    private String rabbitMQUsername;

    @Value("${RABBITMQ_PASSWORD:guest}")
    private String rabbitMQPassword;

    @Bean(name = "rabbitMQHost")
    public String getRabbitMQHost() {
        return rabbitMQHost;
    }

    @Bean(name = "rabbitMQPort")
    public int getRabbitMQPort() {
        return rabbitMQPort;
    }

    @Bean(name = "rabbitMQUsername")
    public String getRabbitMQUsername() {
        return rabbitMQUsername;
    }

    @Bean(name = "rabbitMQPassword")
    public String getRabbitMQPassword() {
        return rabbitMQPassword;
    }

    @Value("${RABBITMQ_EXCHANGE:}")
    private String rabbitMQExchange;

    @Bean(name = "rabbitMQExchange")
    public String getRabbitMQExchange() {
        return rabbitMQExchange;
    }

    @Value("${RABBITMQ_QUEUE_IMAGE:image_queue}")
    private String rabbitMQQueueImage;

    @Bean(name = "rabbitMQQueueImage")
    public String getRabbitMQQueueImage() {
        return rabbitMQQueueImage;
    }

    @Value("${RABBITMQ_QUEUE_TEXT:text_queue}")
    private String rabbitMQQueueText;

    @Bean(name = "rabbitMQQueueText")
    public String getRabbitMQQueueText() {
        return rabbitMQQueueText;
    }

    @Bean(name = "connectionFactory")
    public CachingConnectionFactory getConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQHost, rabbitMQPort);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassword);
        return connectionFactory;
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(rabbitMQExchange);
        template.setMessageConverter(new JacksonJsonMessageConverter());
        return template;
    }
}
