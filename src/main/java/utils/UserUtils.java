package utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import business.TokenService;
import models.Token;
import models.Usuario;

public class UserUtils {
	
	public static boolean enviarMailConfirmacionReset(Usuario user) {
		AccesoProperties accesoProperties = new AccesoProperties();
		Properties prop = accesoProperties.cargarFicheroEmail();
		final String username = prop.getProperty("email-user");
        final String password = prop.getProperty("email-password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
          });

        try {
        	Token token = TokenUtils.generateToken(user);
        	
        	TokenService tokenService = new TokenService();
        	if (!tokenService.caducarTokens(user.getIdUsuario())) {
        		return false;
			}
        	token = tokenService.add(token);
        	
        	if (token == null || token.getToken_id() == null || token.getToken_id() == 0) {
        		return false;
			}
        	
            String emailContent = String.format(template, "http://localhost:8080/rubikTimerWeb/user/resetPassword?token=" + token.getUuid());

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "RubikTimer"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getCorreo()));
            message.setSubject("Solicitud de restablecimiento de contraseña");
            message.setContent(emailContent, "text/html");

            Transport.send(message);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return false;
        }

		return true;
	}
	
	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] encryptedBytes = md.digest();

		StringBuilder sb = new StringBuilder();
		for (byte b : encryptedBytes) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();

	}
	
	private final static String template = "<!DOCTYPE html>\r\n"
			+ "<html>\r\n"
			+ "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n"
			+ "    <title>Restablecimiento de Contrase&ntilde;a</title>\r\n"
			+ "    <style>\r\n"
			+ "        /* Estilos CSS para el correo */\r\n"
			+ "        body {\r\n"
			+ "            font-family: Arial, sans-serif;\r\n"
			+ "            background-color: #f2f2f2;\r\n"
			+ "            margin: 0;\r\n"
			+ "            padding: 0;\r\n"
			+ "            text-align: center;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        .container {\r\n"
			+ "            max-width: 600px;\r\n"
			+ "            margin: 20px auto; \r\n"
			+ "            padding: 20px;\r\n"
			+ "            background-color: #ffffff;\r\n"
			+ "            border-radius: 10px;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        h1 {\r\n"
			+ "            color: #0073e6; /* Color azul */\r\n"
			+ "            font-size: 28px;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        p {\r\n"
			+ "            font-size: 18px;\r\n"
			+ "            color: #333;\r\n"
			+ "            margin-bottom: 15px;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        a {\r\n"
			+ "            color: #4CAF50; /* Color verde */\r\n"
			+ "            text-decoration: none;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        a:hover {\r\n"
			+ "            text-decoration: underline;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        .btn {\r\n"
			+ "            display: inline-block;\r\n"
			+ "            background-color: #4CAF50; /* Color verde */\r\n"
			+ "            color: #fff;\r\n"
			+ "            padding: 10px 20px;\r\n"
			+ "            border-radius: 5px;\r\n"
			+ "            text-decoration: none;\r\n"
			+ "            margin-top: 20px;\r\n"
			+ "            font-weight: bold;\r\n"
			+ "        }\r\n"
			+ "    </style>\r\n"
			+ "</head>\r\n"
			+ "<body>\r\n"
			+ "    <div class=\"container\">\r\n"
			+ "        <h1>Restablecimiento de Contrase&ntilde;a</h1>\r\n"
			+ "        <p>Haz clic en el siguiente enlace para restablecer tu contrase&ntilde;a:</p>\r\n"
			+ "        <p><a href=\"%s\" class=\"btn\">Restablecer Contrase&ntilde;a</a></p>\r\n"
			+ "    </div>\r\n"
			+ "</body>\r\n"
			+ "</html>";
}