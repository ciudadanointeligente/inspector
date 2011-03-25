package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Urgency;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface UrgencyServiceAsync {
	void getAllUrgencies(AsyncCallback<List<Urgency>> callback);
	void getUrgency(Long urgencyId, AsyncCallback<Urgency> callback);
	void saveUrgency(Urgency urgency, AsyncCallback<Urgency> callback);
	void deleteUrgency(Urgency urgency, AsyncCallback<Void> callback);
}
