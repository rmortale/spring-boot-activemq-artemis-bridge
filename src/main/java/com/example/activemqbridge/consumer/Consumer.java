package com.example.activemqbridge.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private JmsTemplate activemqTempl;

    @Autowired
    private JmsTemplate artemisTempl;

    @JmsListener(destination = "mailbox",
            containerFactory = "activemqListenerContainerFactory")
    public void receiveMessageFromActivemq(String email) {
        System.out.println("Received message from qm1");
        artemisTempl.convertAndSend("mailbox1", "from qm1");
    }

    @JmsListener(destination = "mailbox",
            containerFactory = "artemisListenerContainerFactory")
    public void receiveMessageFromArtemis(String email) {
        System.out.println("Received message from qm2");
        activemqTempl.convertAndSend("mailbox2", "from qm2");
    }
}
