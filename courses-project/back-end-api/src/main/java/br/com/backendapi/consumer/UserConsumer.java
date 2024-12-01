package br.com.backendapi.consumer;

import br.com.backendapi.service.rabbitmq.RabbitMQSender;
import br.com.backendapi.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final IUserService userService;
    private final RabbitMQSender rabbitMQSender;

    @Value("${retry.max-attempts:3}")
    private int maxAttempts;

    @RabbitListener(queues = "user.save.queue")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public void consumeMessage(String message) {

        try {

            System.out.println("Received message: " + message);

            processMessage(message);

        } catch (Exception e) {
            throw new RuntimeException("Error processing message, will retry.");
        }

    }

    private void processMessage(String message) throws Exception {
        if (message.equals("fail")) {
            throw new Exception("Simulated failure during processing");
        }
        System.out.println("Message processed: " + message);
    }

    @Recover
    public void recoverMessage(String message, Throwable e) {
        rabbitMQSender.sendMessage("user.exchange", "userddl", message);
    }

}
