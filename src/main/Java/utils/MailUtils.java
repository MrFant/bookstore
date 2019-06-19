package utils;

//import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class MailUtils {
    public static void sendMail(String email,String emailMsg) throws AddressException,MessagingException{
        Properties properties =new Properties();
        properties.setProperty("mail.transport.protocol","SMTP");
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.smtp.auth","true");
        Authenticator authenticator=new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("2413633136@qq.com","vnilclloyvsvecaf");
            }
        };
//        Authenticator authenticator=new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("2413633136@qq.com","vnilclloyvsvecaf".toCharArray());
//            }
//        };
        final String smtpPort = "465";
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session=Session.getInstance(properties,authenticator);
        Message message=new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("2413633136@qq.com"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        message.setSubject("用户激活");
        message.setContent(emailMsg,"text/html;charset=utf-8");

//        message.saveChanges();
        Transport.send(message);

    }

    public static void main(String[] args) {
        String email="20152835320046@hainu.edu.cn";
        String emailMsg="sb ke";
        try {
            MailUtils.sendMail(email,emailMsg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
