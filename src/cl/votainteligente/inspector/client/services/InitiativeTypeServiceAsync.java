package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.InitiativeType;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface InitiativeTypeServiceAsync {
	void getAllInitiativeTypes(AsyncCallback<List<InitiativeType>> callback);
	void getInitiativeType(Long initiativeTypeId, AsyncCallback<InitiativeType> callback);
	void saveInitiativeType(InitiativeType initiativeType, AsyncCallback<InitiativeType> callback);
	void deleteInitiativeType(InitiativeType initiativeType, AsyncCallback<Void> callback);
}
