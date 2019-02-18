package com.example.rabbitmq.reciever;

import java.util.concurrent.CountDownLatch;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	// countdownlatch is way to signal that message received
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }
    
    // this not working but think because of existing code defining the listener in the application code
	@RabbitListener(queues="kevtest")
    public void recievedMessage(String msg) {
        System.out.println("Recieved Message as MDP with Listener annotation : " + msg);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}