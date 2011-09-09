package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.RecaptchaRemoteService;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

import org.gwtwidgets.server.spring.ServletUtils;
import org.hibernate.SessionFactory;

public class RecaptchaRemoteServiceImpl implements RecaptchaRemoteService {
	private String publicKey = "6Lec-ccSAAAAAMqR4VuGXbnvKBRT6GtCy_IkUHgx";
	private String privateKey = "6Lec-ccSAAAAANXjulL5h425qammdgsKP8K2niDC";
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean verifyChallenge(String challenge, String response) throws Exception {
		try {
			ReCaptcha r = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, true);
			String remoteAddress = ServletUtils.getRequest().getRemoteAddr().toString();
			return r.checkAnswer(remoteAddress, challenge, response).isValid();
		} catch (Exception ex) {
			throw ex;
		}
	}
}