package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Society;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface SocietyServiceAsync {
	void getAllSocieties(AsyncCallback<List<Society>> callback);
	void getSociety(Long societyId, AsyncCallback<Society> callback);
	void saveSociety(Society society, AsyncCallback<Society> callback);
	void deleteSociety(Society society, AsyncCallback<Void> callback);
	void getSocietiesByBill(Bill bill, AsyncCallback<List<Society>> callback);
}
