package br.com.backendapi.handler.rabbitmq;

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
    public Binding registryUserSaveQueue(TopicExchange userExchange) {

        Queue queue = new Queue("save", true);

        return BindingBuilder.bind(queue).to(userExchange).with("save");

    }

    @Bean
    public Binding registryUserDDL(TopicExchange userExchange) {

        Queue queue = new Queue("userddl", true);

        return BindingBuilder.bind(queue).to(userExchange).with("userddl");

    }

}
