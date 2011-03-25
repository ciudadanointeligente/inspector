package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Stage;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface StageServiceAsync {
	void getAllStages(AsyncCallback<List<Stage>> callback);
	void getStage(Long stageId, AsyncCallback<Stage> callback);
	void saveStage(Stage stage, AsyncCallback<Stage> callback);
	void deleteStage(Stage stage, AsyncCallback<Void> callback);
}
