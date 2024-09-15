package com.example.activemqbridge.configuration;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class AmqConfig {

    @Bean
    public ConnectionFactory activemqCf(
            @Value("${app.activemq.url}") String url,
            @Value("${app.activemq.username}") String username,
            @Value("${app.activemq.password}") String password) {
        return new CachingConnectionFactory(
                new ActiveMQConnectionFactory(
                        username, password, url));
    }

    @Bean
    public ConnectionFactory artemisCf(
            @Value("${app.artemis.url}") String url,
            @Value("${app.artemis.username}") String username,
            @Value("${app.artemis.password}") String password) {
        return new CachingConnectionFactory(
                new org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory(
                        url, username, password));
    }

    @Bean
    public JmsListenerContainerFactory<?> activemqListenerContainerFactory(
            ConnectionFactory activemqCf,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, activemqCf);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> artemisListenerContainerFactory(
            ConnectionFactory artemisCf,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, artemisCf);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public JmsTemplate activemqTempl(ConnectionFactory activemqCf) {
        JmsTemplate t = new JmsTemplate(activemqCf);
        t.setSessionTransacted(true);
        return t;
    }

    @Bean
    public JmsTemplate artemisTempl(ConnectionFactory artemisCf) {
        JmsTemplate t = new JmsTemplate(artemisCf);
        t.setSessionTransacted(true);
        return t;
    }

}
