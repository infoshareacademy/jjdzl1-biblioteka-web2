package com.infoshare.logic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GoogleMail {

    public static final Logger LOGGER = Logger.getLogger(GoogleMail.class.getName());


    public static void sendMail(String to, String message, String bookTitle) {

        Properties props = new Properties();

        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bibliotekaisa@gmail.com", "infoshareacademy");
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            InternetAddress[] address = InternetAddress.parse(to, true);
            msg.setRecipients(Message.RecipientType.TO, address);
            String timeStamp = new SimpleDateFormat("yyyymmdd_hh-mm-ss").format(new Date());
            msg.setSubject("Przypomnienie o zaległym zwrocie książki : " + timeStamp);
            msg.setSentDate(new Date());
            msg.setText(message);
            msg.setHeader("XPriority", "1");

            Transport.send(msg);
            LOGGER.info("Mail has been sent successfully");

        } catch (MessagingException mex) {

            LOGGER.info("Unable to send an email" + mex);
        }
    }
}