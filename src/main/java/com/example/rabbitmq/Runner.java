package com.example.rabbitmq;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.rabbitmq.reciever.Receiver;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    // use rabbitMQTemplate to send message to exchange using the correct routing key (i.e. topic starts with foo.bar)
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        System.out.println("Sending message to kev-test for MDP listener to consume ");
        rabbitTemplate.convertAndSend("kev-exchange", "red", "Hello from RabbitMQ MDP!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}