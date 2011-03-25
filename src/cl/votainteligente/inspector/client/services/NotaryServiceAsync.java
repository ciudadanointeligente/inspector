package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Notary;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface NotaryServiceAsync {
	void getAllNotaries(AsyncCallback<List<Notary>> callback);
	void getNotary(Long notaryId, AsyncCallback<Notary> callback);
	void saveNotary(Notary notary, AsyncCallback<Notary> callback);
	void deleteNotary(Notary notary, AsyncCallback<Void> callback);
}
