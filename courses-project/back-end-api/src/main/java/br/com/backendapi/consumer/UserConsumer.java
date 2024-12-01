package br.com.backendapi.consumer;

import br.com.backendapi.domain.user.UserRegisterDTO;
import br.com.backendapi.service.rabbitmq.RabbitMQSender;
import br.com.backendapi.service.user.IUserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final IUserService userService;
    private final RabbitMQSender rabbitMQSender;

    @RabbitListener(queues = "save")
    public void consumeMessage(String message) {

        try {

            UserRegisterDTO userRegisterDTO = new Gson().fromJson(message, UserRegisterDTO.class);

            userService.create(userRegisterDTO);

        } catch (Exception e) {
            rabbitMQSender.handleException(message, "user.exchange", "save", "ddluser");
        }

    }

}
