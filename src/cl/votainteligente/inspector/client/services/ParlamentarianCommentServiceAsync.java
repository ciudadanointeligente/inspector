package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.ParlamentarianComment;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ParlamentarianCommentServiceAsync {
	void getAllParlamentarianComments(Long parlamentarianId, AsyncCallback<List<ParlamentarianComment>> callback);
	void getParlamentarianComment(Long parlamentarianCommentId, AsyncCallback<ParlamentarianComment> callback);
	void saveParlamentarianComment(ParlamentarianComment parlamentarianComment, Long parlamentarianId, AsyncCallback<ParlamentarianComment> callback);
	void approveParlamentarianComment(String key, Long id, Long parlamentarianId, AsyncCallback<ParlamentarianComment> callback);
	void rejectParlamentarianComment(String key, Long id, Long parlamentarianId, AsyncCallback<ParlamentarianComment> callback);
	void deleteParlamentarianComment(ParlamentarianComment parlamentarianComment, AsyncCallback<Void> callback);
}
