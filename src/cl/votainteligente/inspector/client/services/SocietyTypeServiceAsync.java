package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.SocietyType;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface SocietyTypeServiceAsync {
	void getAllSocietyTypes(AsyncCallback<List<SocietyType>> callback);
	void getSocietyType(Long societyTypeId, AsyncCallback<SocietyType> callback);
	void saveSocietyType(SocietyType societyType, AsyncCallback<SocietyType> callback);
	void deleteSocietyType(SocietyType societyType, AsyncCallback<Void> callback);
}
