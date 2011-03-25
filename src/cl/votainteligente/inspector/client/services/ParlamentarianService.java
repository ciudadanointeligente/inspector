package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/parlamentarian")
public interface ParlamentarianService extends RemoteService {
	List<Parlamentarian> getAllParlamentarians() throws Exception;
	Parlamentarian getParlamentarian(Long parlamentarianId) throws Exception;
	Parlamentarian saveParlamentarian(Parlamentarian parlamentarian) throws Exception;
	void deleteParlamentarian(Parlamentarian parlamentarian) throws Exception;
}