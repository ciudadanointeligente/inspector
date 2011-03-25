package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface BillServiceAsync {
	void getAllBills(AsyncCallback<List<Bill>> callback);
	void getBill(Long billId, AsyncCallback<Bill> callback);
	void saveBill(Bill bill, AsyncCallback<Bill> callback);
	void deleteBill(Bill bill, AsyncCallback<Void> callback);
}
