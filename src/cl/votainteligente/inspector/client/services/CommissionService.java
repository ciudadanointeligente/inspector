package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Commission;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/commission")
public interface CommissionService extends RemoteService {
	List<Commission> getAllCommissions() throws Exception;
	Commission getCommission(Long commissionId) throws Exception;
	Commission saveCommission(Commission commission) throws Exception;
	void deleteCommission(Commission commission) throws Exception;
}