package com.marko.consumer;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Component - marks this class as a bean,
 * so the component scanning mechanism of Spring
 * can pick it up and pull it into application context
 */

@Component
@PropertySource("classpath:application.properties")
public class Receiver {

    private static final Logger LOGGER = Logger.getLogger(Receiver.class);

    /**
     * for testing convenience
     * allows the POJO to signal that the message is received
     * (bad practice)
     */
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    /**
     * @JmsListener creates a message listener container
     * behind the scenes using a JmsListenerContainerFactory
     * setup in the receiver config class
     */
    @JmsListener(destination = "${activemq.queue.destination}")
    public void receive(final String message) {
        LOGGER.info("Message received=" + message + "\n");
        countDownLatch.countDown();
    }
}
