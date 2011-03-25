package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.BillType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/billType")
public interface BillTypeService extends RemoteService {
	List<BillType> getAllBillTypes() throws Exception;
	BillType getBillType(Long billTypeId) throws Exception;
	BillType saveBillType(BillType billType) throws Exception;
	void deleteBillType(BillType billType) throws Exception;
}