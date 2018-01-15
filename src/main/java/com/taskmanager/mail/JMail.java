package com.taskmanager.mail;


import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JMail {

    private String fromEmail; // - адрес эл. почты отправителя
    private String fromFullname; // - имя отправителя
    private String emailUser; // - адрес эл. почты получателя
    private String fullnameUser; // - имя получателя
    private String subject = "Подтверждение email"; // - тема письма
    private String body; // - тело письма
    private String hostMail = "smtp.mail.ru"; // - хост почтового сервера
    private String mailUser = "drek6@mail.ru"; // - имя пользователя, для авторизации на почтовом сервере
    private String mailPassword = "214563286437915214563286437915q"; // - пароль пользователя, для авторизации на почтовом сервере

    public JMail(String fromEmail, String fromFullname, String emailUser, String fullnameUser, String body) {
        this.fromEmail = fromEmail;
        this.fromFullname = fromFullname;
        this.emailUser = emailUser;
        this.fullnameUser = fullnameUser;
        this.body = body;
    }

    public void sendMail ()  {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session_m = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser,mailPassword);}
            });

        try {
            MimeMessage message = new MimeMessage(session_m);
            message.setFrom (new InternetAddress(fromEmail,fromFullname));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailUser, fullnameUser));

            message.setSubject(subject);
            message.setText(body);
            message.setHeader("Content-Type","text/plain;charset=windows-1251");

            SMTPTransport t = (SMTPTransport) session_m.getTransport("smtp");
            t.connect(hostMail, mailUser, mailPassword);
            t.sendMessage(message, message.getAllRecipients());
            System.out.println(message + " Email:" + emailUser);
        }
        catch (Exception e) {
            System.err.println(e);
            System.out.println("Email: " + emailUser);
        }
    }
}
