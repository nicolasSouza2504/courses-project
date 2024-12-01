package br.com.courses.handler.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Binding saveBinding() {

        ExchangeBuilder.topicExchange("user.exchange")
                .durable(true)
                .build();

        Queue queue = new Queue("save", true);

        return BindingBuilder.bind(queue).to(new TopicExchange("user.exchange")).with("save");
    }

}
