package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.RecaptchaRemoteService;
import cl.votainteligente.inspector.server.ApplicationProperties;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

import org.gwtwidgets.server.spring.ServletUtils;
import org.hibernate.SessionFactory;

public class RecaptchaRemoteServiceImpl implements RecaptchaRemoteService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean verifyChallenge(String challenge, String response) throws Exception {
		try {
			ReCaptcha r = ReCaptchaFactory.newReCaptcha(ApplicationProperties.getProperty("recaptcha.publicKey"), ApplicationProperties.getProperty("recaptcha.privateKey"), true);
			String remoteAddress = ServletUtils.getRequest().getRemoteAddr().toString();
			return r.checkAnswer(remoteAddress, challenge, response).isValid();
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public String getPublicKey() throws Exception {
		try {
			return ApplicationProperties.getProperty("recaptcha.publicKey");
		} catch (Exception ex) {
			throw ex;
		}
	}
}