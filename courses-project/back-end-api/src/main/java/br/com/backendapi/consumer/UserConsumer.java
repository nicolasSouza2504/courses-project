package br.com.backendapi.consumer;

import br.com.backendapi.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private IUserService userService;

    private final Integer maxAttempts = 3;

    @RabbitListener(queues = "save")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5)) // Retry 3 times with exponential backoff
    public void consumeMessage(String message) {
        try {
            System.out.println("Received message: " + message);
            // Simulate some processing logic, you can throw exceptions to trigger retry
            processMessage(message);
        } catch (Exception e) {
            System.err.println("Error while processing message: " + e.getMessage());
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
        System.err.println("Failed to process message after " + maxAttempts + " attempts. Discarding message: " + message);
    }

    @RabbitListener(queues = "ddluser")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5)) // Retry 3 times with exponential backoff
    public void consumeMessageDDL(String message) {
        try {
            System.out.println("Received message: " + message);
            // Simulate some processing logic, you can throw exceptions to trigger retry
            processMessage(message);
        } catch (Exception e) {
            System.err.println("Error while processing message: " + e.getMessage());
            throw new RuntimeException("Error processing message, will retry.");
        }
    }


}
