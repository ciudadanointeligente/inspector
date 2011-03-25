package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ParlamentarianServiceAsync {
	void getAllParlamentarians(AsyncCallback<List<Parlamentarian>> callback);
	void getParlamentarian(Long parlamentarianId, AsyncCallback<Parlamentarian> callback);
	void saveParlamentarian(Parlamentarian parlamentarian, AsyncCallback<Parlamentarian> callback);
	void deleteParlamentarian(Parlamentarian parlamentarian, AsyncCallback<Void> callback);
}
