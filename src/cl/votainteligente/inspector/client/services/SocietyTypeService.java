package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.SocietyType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/societyType")
public interface SocietyTypeService extends RemoteService {
	List<SocietyType> getAllSocietyTypes() throws Exception;
	SocietyType getSocietyType(Long societyTypeId) throws Exception;
	SocietyType saveSocietyType(SocietyType societyType) throws Exception;
	void deleteSocietyType(SocietyType societyType) throws Exception;
}