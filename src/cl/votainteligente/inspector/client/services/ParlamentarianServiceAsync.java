package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ParlamentarianServiceAsync {
	void getAllParlamentarians(AsyncCallback<List<Parlamentarian>> callback);
	void getParlamentarian(Long parlamentarianId, AsyncCallback<Parlamentarian> callback);
	void saveParlamentarian(Parlamentarian parlamentarian, AsyncCallback<Parlamentarian> callback);
	void deleteParlamentarian(Parlamentarian parlamentarian, AsyncCallback<Void> callback);
	void searchParlamentarian(String keyWord, AsyncCallback<List<Parlamentarian>> callback);
	void searchParlamentarian(Category category, AsyncCallback<List<Parlamentarian>> callback);
	void getParlamentariansByBill(Bill bill, AsyncCallback<List<Parlamentarian>> callback);
	void getBillAuthors(Bill bill, AsyncCallback<List<Parlamentarian>> callback);
}
