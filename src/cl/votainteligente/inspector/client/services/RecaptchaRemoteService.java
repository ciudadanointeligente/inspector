package cl.votainteligente.inspector.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwtrpc/recaptcha")
public interface RecaptchaRemoteService extends RemoteService {

	public boolean verifyChallenge(String challenge, String response) throws Exception;

	public static class Util {
		public static RecaptchaRemoteServiceAsync getInstance() {
			return GWT.create(RecaptchaRemoteService.class);
		}
	}
}