package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Chamber;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/chamber")
public interface ChamberService extends RemoteService {
	List<Chamber> getAllChambers() throws Exception;
	Chamber getChamber(Long chamberId) throws Exception;
	Chamber saveChamber(Chamber chamber) throws Exception;
	void deleteChamber(Chamber chamber) throws Exception;
}