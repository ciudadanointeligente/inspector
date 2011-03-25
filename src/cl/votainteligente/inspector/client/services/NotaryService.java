package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Notary;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/notary")
public interface NotaryService extends RemoteService {
	List<Notary> getAllNotaries() throws Exception;
	Notary getNotary(Long notaryId) throws Exception;
	Notary saveNotary(Notary notary) throws Exception;
	void deleteNotary(Notary notary) throws Exception;
}