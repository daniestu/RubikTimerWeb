package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    private static final Logger _log = LogManager.getLogger(EmailUtils.class);

    private static Session getMailSession() {
        final String username = AccesoProperties.getRequiredEnv("MAIL_USERNAME");
        final String password = AccesoProperties.getRequiredEnv("MAIL_PASSWORD");
        final String mailHost = AccesoProperties.getRequiredEnv("MAIL_HOST");
        final String mailPort = AccesoProperties.getRequiredEnv("MAIL_PORT");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public static boolean enviarEmail(String destinatario, String asunto, String contenidoHtml) {
        try {
            String mailFrom = System.getenv("MAIL_FROM") != null ? System.getenv("MAIL_FROM") : "support1@dtimerapp.com";
            Session session = getMailSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom, "DTimer"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setContent(contenidoHtml, "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (Exception e) {
            _log.error(e.getMessage(), e);
            return false;
        }
    }
}
