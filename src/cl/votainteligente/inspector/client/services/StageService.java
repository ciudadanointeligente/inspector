package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Stage;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/stage")
public interface StageService extends RemoteService {
	List<Stage> getAllStages() throws Exception;
	Stage getStage(Long stageId) throws Exception;
	Stage saveStage(Stage stage) throws Exception;
	void deleteStage(Stage stage) throws Exception;
}