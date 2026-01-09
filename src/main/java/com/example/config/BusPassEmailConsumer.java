package com.example.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageConsumer;
import jakarta.jms.TextMessage;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class BusPassEmailConsumer implements ServletContextListener {
	
	 //Note : if you want to use this you should disable the Topic Active MQ OfferEmailConsumerListener in web.xml


    private Connection connection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            connection = factory.createConnection();
            connection.start();

            jakarta.jms.Session jmsSession =
                    connection.createSession(false, jakarta.jms.Session.AUTO_ACKNOWLEDGE);

            Destination queue = jmsSession.createQueue("busPassEmailQueue");

            MessageConsumer consumer = jmsSession.createConsumer(queue);

            consumer.setMessageListener(message -> {
                try {
                    TextMessage textMessage = (TextMessage) message;

                    String[] data = textMessage.getText().split(";");
                    String email = data[0];
                    String name = data[1];

                    sendEmail(email, name);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            System.out.println("ActiveMQ Email Consumer Started...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (connection != null) connection.close();
        } catch (Exception ignored) {}
    }


    private void sendEmail(String toEmail, String name) {

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

            msg.setSubject("Bus Pass Registration Successful");
            msg.setText("Hello " + name +
                    ",\n\nYour Bus Pass Registration is Successful.\n\nThank You!");

            Transport.send(msg);

            System.out.println("Email Sent To: " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
