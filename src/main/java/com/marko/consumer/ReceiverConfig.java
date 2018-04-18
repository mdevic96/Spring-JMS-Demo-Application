package com.marko.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

/**
 * @EnableJms enables support for the @JmsListener
 * that was used in the Receiver POJO class
 */
@Configuration
@EnableJms
@PropertySource("classpath:application.properties")
public class ReceiverConfig {

    /**
     * @Value injects value from application.properties file
     * by inserting key value
     */
    @Value(value = "${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value(value = "${spring.activemq.user}")
    private String username;

    @Value(value = "${spring.activemq.password}")
    private String password;

    /**
     * @Bean tells Spring that the method under this ann.
     * will return an object registered as a bean
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);

        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());

        return factory;
    }
}
