package com.example.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

public class OfferProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(OfferProducer.class); 

    public static void publishOffer(String offerMessage) {

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("busPassOfferTopic");

            MessageProducer producer = session.createProducer(topic);

            TextMessage message = session.createTextMessage(offerMessage);

            producer.send(message);

            session.close();
            connection.close();

            LOG.info("Offer published to topic");

        } catch (Exception e) {
        	
            e.printStackTrace();
        }
    }
}

