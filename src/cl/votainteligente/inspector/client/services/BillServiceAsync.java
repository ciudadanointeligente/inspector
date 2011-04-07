package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface BillServiceAsync {
	void getAllBills(AsyncCallback<List<Bill>> callback);
	void getBill(Long billId, AsyncCallback<Bill> callback);
	void saveBill(Bill bill, AsyncCallback<Bill> callback);
	void deleteBill(Bill bill, AsyncCallback<Void> callback);
	void searchBills(Parlamentarian parlamentarian, Category category, AsyncCallback<List<Bill>> callback);
}
