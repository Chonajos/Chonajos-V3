package com.web.chon.util;

import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
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

    public static void sendTLS() {

        final String username = "chonajosmx@gmail.com";
        final String password = "Ch0najos2017";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("juancruzh91@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("juancruzh91@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please! TLS");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendAdjunto(String asunto, String mensaje, ArrayList<FileDataSource> lstFileDataSource, String correoDe, ArrayList<String> lstCorreoPara) {

        final String username = "chonajosmx@gmail.com";
        final String password = "Ch0najos2017";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMultipart multiParte = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);

            BodyPart adjunto = new MimeBodyPart();

            //Se preparan los archivos adjuntos
            if (lstFileDataSource != null) {
                for (FileDataSource fileDataSource : lstFileDataSource) {
                    adjunto.setDataHandler(new DataHandler(fileDataSource));
                    adjunto.setFileName(fileDataSource.getName());
                    multiParte.addBodyPart(adjunto);

                }
            }

            multiParte.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);

            // Se mete el texto y la foto adjunta.
            message.setContent(multiParte);

            // Se rellena el From
            message.setFrom(new InternetAddress(correoDe));

            // Se rellenan los destinatarios
            for (String correoPara : lstCorreoPara) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            }

            // Se rellena el subject
            message.setSubject(asunto);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private int count = 0;

    public void write(int b) {
        count++;
    }

    public int getCount() {
        return count;
    }

}
