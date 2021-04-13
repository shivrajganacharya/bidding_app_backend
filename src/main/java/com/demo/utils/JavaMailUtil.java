package com.demo.utils;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailUtil {
    public static void sendMail(String recepient, String textMessage) throws MessagingException, javax.mail.MessagingException {
        System.out.println("Preparing to sent email.");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "dr.abhishek604@gmail.com";
        String password = "****";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recepient, textMessage);

        Transport.send(message);
        System.out.println("Message sent successfully.");

    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepint, String textMessage) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepint));
            message.setSubject("Bidding Result");
            message.setText(textMessage);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}