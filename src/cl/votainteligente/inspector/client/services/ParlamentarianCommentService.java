package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.ParlamentarianComment;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/parlamentarianComment")
public interface ParlamentarianCommentService extends RemoteService {
	List<ParlamentarianComment> getAllParlamentarianComments() throws Exception;
	ParlamentarianComment getParlamentarianComment(Long parlamentarianCommentId) throws Exception;
	ParlamentarianComment saveParlamentarianComment(ParlamentarianComment parlamentarianComment) throws Exception;
	ParlamentarianComment aproveParlamentarianComment(String key) throws Exception;
	void deleteParlamentarianComment(ParlamentarianComment parlamentarianComment) throws Exception;
}
