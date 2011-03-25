package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Urgency;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/urgency")
public interface UrgencyService extends RemoteService {
	List<Urgency> getAllUrgencies() throws Exception;
	Urgency getUrgency(Long urgencyId) throws Exception;
	Urgency saveUrgency(Urgency urgency) throws Exception;
	void deleteUrgency(Urgency urgency) throws Exception;
}