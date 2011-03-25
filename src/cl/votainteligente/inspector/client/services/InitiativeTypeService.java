package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.InitiativeType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/initiativeType")
public interface InitiativeTypeService extends RemoteService {
	List<InitiativeType> getAllInitiativeTypes() throws Exception;
	InitiativeType getInitiativeType(Long initiativeTypeId) throws Exception;
	InitiativeType saveInitiativeType(InitiativeType initiativeType) throws Exception;
	void deleteInitiativeType(InitiativeType initiativeType) throws Exception;
}