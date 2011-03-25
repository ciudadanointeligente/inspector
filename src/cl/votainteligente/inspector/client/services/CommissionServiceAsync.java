package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Commission;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface CommissionServiceAsync {
	void getAllCommissions(AsyncCallback<List<Commission>> callback);
	void getCommission(Long commissionId, AsyncCallback<Commission> callback);
	void saveCommission(Commission commission, AsyncCallback<Commission> callback);
	void deleteCommission(Commission commission, AsyncCallback<Void> callback);
}
