package com.marko;

import com.marko.consumer.Receiver;
import com.marko.producer.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:application-test.properties")
@DirtiesContext
public class JmsDemoApplicationTests {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testReceive() throws Exception {
        sender.send("helloworld.q", "Hello, World!");

        receiver.getCountDownLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getCountDownLatch().getCount()).isEqualTo(0);
    }

    @Test
    public void testReceiveMessage() {
        sender.send("helloworld.q", "hello world");
        assertThat(jmsTemplate.receiveAndConvert("helloworld.q")).isEqualTo("HELLO WORLD");
    }
}
