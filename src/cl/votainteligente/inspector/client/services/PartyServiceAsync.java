package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Party;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface PartyServiceAsync {
	void getAllParties(AsyncCallback<List<Party>> callback);
	void getParty(Long partyId, AsyncCallback<Party> callback);
	void saveParty(Party party, AsyncCallback<Party> callback);
	void deleteParty(Party party, AsyncCallback<Void> callback);
}
