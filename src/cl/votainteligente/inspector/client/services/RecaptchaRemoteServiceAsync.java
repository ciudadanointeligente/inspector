package cl.votainteligente.inspector.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecaptchaRemoteServiceAsync {
		public void verifyChallenge(String challenge, String response, AsyncCallback<Boolean> callback);
		void getPublicKey(AsyncCallback<String> callback);
}
