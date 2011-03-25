package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.BillType;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface BillTypeServiceAsync {
	void getAllBillTypes(AsyncCallback<List<BillType>> callback);
	void getBillType(Long billTypeId, AsyncCallback<BillType> callback);
	void saveBillType(BillType billType, AsyncCallback<BillType> callback);
	void deleteBillType(BillType billType, AsyncCallback<Void> callback);
}
