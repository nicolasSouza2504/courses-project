package br.com.courses.handler.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange("user.exchange", true, false);
    }

    @Bean
    public Queue saveQueue() {
        return new Queue("save", true);
    }

    @Bean
    public Binding saveBinding() {
        return BindingBuilder.bind(saveQueue())
                .to(userExchange())
                .with("save");
    }

}
