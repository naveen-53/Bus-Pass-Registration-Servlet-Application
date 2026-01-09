package com.example.config;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageConsumer;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

import com.example.dao.BusPassDAO;
import com.example.model.BusPass;

public class OfferEmailConsumerListener implements ServletContextListener {
	

	 // Note : if you want to  use this you should disable the Queue Active MQ BusPassEmailConsumer listener in web.xml
	 
	private static final Logger LOG = LoggerFactory.getLogger(OfferEmailConsumerListener.class);
	
    private Connection connection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ConnectionFactory factory =
                    new ActiveMQConnectionFactory("tcp://localhost:61616");

            connection = factory.createConnection();

            connection.setClientID("offer-email-consumer");

            connection.start();
            LOG.info("Consumer Connection started");

            jakarta.jms.Session session =
                    connection.createSession(false, jakarta.jms.Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("busPassOfferTopic");

            MessageConsumer consumer =
                    session.createDurableSubscriber(topic, "email-subscriber");

            consumer.setMessageListener(message -> {
                try {
                    TextMessage text = (TextMessage) message;
                    sendOfferEmailToAll(text.getText());
                    LOG.info("Consumer Connection started");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Offer Email Consumer Started");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendOfferEmailToAll(String offerMessage) {

        try {
            BusPassDAO dao = new BusPassDAO();
            List<BusPass> passes = dao.findAll();
            
            for (BusPass pass : passes) {
            	String email = pass.getEmail();
            	if (email == null || email.isBlank()) {
                    LOG.warn("Skipping BusPass id={} due to null email", pass.getId());
                    continue;
                }
                sendEmail(email, offerMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String toEmail, String offerMessage) {

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session mailSession = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "naveennk5302@gmail.com",
                                    "jjvw lvgj wgld qvjb");
                        }
                    });

            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress("naveennk5302@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            msg.setSubject("New Bus Pass Offer");
            msg.setText(offerMessage);

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (connection != null) {
            	connection.close();
            	LOG.info("Consumer Connection closed");
            }
            
        } catch (Exception ignored) {}
    }
}

