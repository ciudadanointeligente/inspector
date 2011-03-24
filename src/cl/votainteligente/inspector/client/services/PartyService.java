package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Party;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/party")
public interface PartyService extends RemoteService {
	List<Party> getAllParties() throws Exception;
	Party getParty(Long partyId) throws Exception;
	Party saveParty(Party party) throws Exception;
	void deleteParty(Party party) throws Exception;
}
