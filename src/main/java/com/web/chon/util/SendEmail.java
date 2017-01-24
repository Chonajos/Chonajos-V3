package com.web.chon.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase para enviar correo
 *
 * @author Juan
 */
public class SendEmail {

    public static void send() {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
//        =
//=
//=
//
//
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("juancruzh91@gmail.com", "juancruzh91");
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("test@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("juancruzh91@gmail.com"));
            message.setSubject("Testing Subject");

            BodyPart body = new MimeBodyPart();

            // freemarker stuff.
//            Configuration cfg = new Configuration();
//            Template template = cfg.getTemplate("html-mail-template.ftl");
            Map<String, String> rootMap = new HashMap<String, String>();
            rootMap.put("to", "Bharat Sharma");
            rootMap.put("body", "Sample html email using freemarker");
            rootMap.put("from", "Vijaya.");
            Writer out = new StringWriter();
//            template.process(rootMap, out);
            // freemarker stuff ends.

            /* you can add html tags in your text to decorate it. */
            body.setContent(out.toString(), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);

            body = new MimeBodyPart();

//            String filename = "hello.txt";
//            DataSource source = new FileDataSource(filename);
//            body.setDataHandler(new DataHandler(source));
//            body.setFileName(filename);
//            multipart.addBodyPart(body);
            message.setContent(multipart, "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println("Done....");
    }
}
