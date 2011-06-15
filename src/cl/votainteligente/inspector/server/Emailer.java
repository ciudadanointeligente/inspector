package cl.votainteligente.inspector.server;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Emailer {
	private static final Logger logger = Logger.getLogger(Emailer.class);
	private Properties props;
	private String recipient;
	private String subject;
	private String body;

	public Emailer() {
		props = System.getProperties();
	}

	public Emailer(String recipient, String subject, String body) {
		props = System.getProperties();
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean connectAndSend() {
		if (ApplicationProperties.getProperty("email.smtp") == null || ApplicationProperties.getProperty("email.user") == null || ApplicationProperties.getProperty("email.password") == null) {
			logger.error(ApplicationProperties.getProperty("email.error.userNotSet"));
			return false;
		}

		props.put("mail.smtp.host", ApplicationProperties.getProperty("email.smtp"));
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		if (getRecipient() == null || getSubject() == null || getBody() == null) {
			logger.error(ApplicationProperties.getProperty("mail.error.elementsNotSet"));
			return false;
		}

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ApplicationProperties.getProperty("email.user"), ApplicationProperties.getProperty("email.password"));
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ApplicationProperties.getProperty("email.user")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(getRecipient()));
			message.setSubject(getSubject(), "UTF-8");
			message.setContent(getBody(), "text/html;charset=UTF-8");
			message.setSentDate(new Date());
			Transport.send(message);
			return true;
		} catch (MessagingException ex) {
			logger.error(ApplicationProperties.getProperty("email.error.notSent"), ex);
		}

		return false;
	}
}
