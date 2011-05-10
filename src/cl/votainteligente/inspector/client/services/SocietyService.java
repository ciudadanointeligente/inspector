package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Society;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/society")
public interface SocietyService extends RemoteService {
	List<Society> getAllSocieties() throws Exception;
	Society getSociety(Long societyId) throws Exception;
	Society saveSociety(Society society) throws Exception;
	void deleteSociety(Society society) throws Exception;
	List<Society> getSocietiesByBill(Bill bill) throws Exception;
}