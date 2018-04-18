package com.marko.producer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private static final Logger LOGGER = Logger.getLogger(Sender.class);

    /**
     * @Autowired wires and creates an object from Configuration Annotated class
     */
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String message) {
        LOGGER.info("sending message=" + message + " to destination=" + destination);

        /*
         * sends the given object to the specified destination,
         * converting the object to a JMS Message
         *
         * (toUpperCase is for testing purposes)
         */
        jmsTemplate.convertAndSend(destination, message.toUpperCase());
    }
}
