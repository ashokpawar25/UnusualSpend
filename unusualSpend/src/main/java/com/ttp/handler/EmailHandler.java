package com.ttp.handler;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailHandler
{
    public String sendEmail(String subject, String body, String bEmail) {

        if (bEmail.isEmpty()) return "One recipient should be provide";
        if (subject.isEmpty() || body.isEmpty()) return "Please check subject or Body";
        String fromEmail = "ashokpawar8020@gmail.com";
        String toEmail = bEmail;
        String password = "taaf sqgr jhzd hcwb";


        String host = "127.0.0.1";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }


        return "Email Sent Successfully";
    }
}
