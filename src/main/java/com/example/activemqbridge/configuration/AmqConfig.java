package com.example.activemqbridge.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class AmqConfig {

    @Bean
    public ConnectionFactory cf1() {

        return new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:64616");
    }

    @Bean
    public ConnectionFactory cf2() {
        return new org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory("tcp://localhost:63616", "admin", "admin");
    }

    @Bean
    public JmsListenerContainerFactory<?> cf1ListenerContainerFactory(
            @Qualifier("cf1") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> cf2ListenerContainerFactory(
            @Qualifier("cf2") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public JmsTemplate templ1(@Qualifier("cf1") ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public JmsTemplate templ2(@Qualifier("cf2") ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

}
