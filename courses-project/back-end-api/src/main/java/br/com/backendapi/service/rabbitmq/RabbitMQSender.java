package br.com.backendapi.service.rabbitmq;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public void handleException(String message, String exchange, String queue, String ddlQueue) {

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        Integer attempts = 0;

        try {
            attempts = jsonObject.get("attempts").getAsInt();
        } catch (Throwable t) {}

        if (attempts < 3) {

            jsonObject.addProperty("attempts", attempts + 1);

            sendMessage(exchange, queue, jsonObject.toString());

        } else {
            sendMessage(exchange, ddlQueue, message);
        }

    }

}
