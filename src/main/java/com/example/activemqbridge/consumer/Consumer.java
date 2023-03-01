package com.example.activemqbridge.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    @Qualifier("templ1")
    private JmsTemplate template1;

    @Autowired
    @Qualifier("templ2")
    private JmsTemplate template2;

    @JmsListener(destination = "mailbox", containerFactory = "cf1ListenerContainerFactory")
    public void receiveMessageFromcf1(String email) {
        System.out.println("Received message from qm1");
        template2.convertAndSend("mailbox1", "from qm1");
    }

    @JmsListener(destination = "mailbox", containerFactory = "cf2ListenerContainerFactory")
    public void receiveMessageFromCf2(String email) {
        System.out.println("Received message from qm2");
        template1.convertAndSend("mailbox2", "from qm2");
    }
}
