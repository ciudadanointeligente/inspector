package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Chamber;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ChamberServiceAsync {
	void getAllChambers(AsyncCallback<List<Chamber>> callback);
	void getChamber(Long chamberId, AsyncCallback<Chamber> callback);
	void saveChamber(Chamber chamber, AsyncCallback<Chamber> callback);
	void deleteChamber(Chamber chamber, AsyncCallback<Void> callback);
}
