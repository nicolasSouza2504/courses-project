package br.com.courses.service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSender {

    private final AmqpTemplate amqpTemplate;

    public void sendMessage(String exchangeName, String routing, String message) {

        amqpTemplate.convertAndSend(
                exchangeName,
                routing,
                message
        );

    }

}
