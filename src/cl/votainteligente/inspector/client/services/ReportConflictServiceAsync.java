package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.ReportConflict;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ReportConflictServiceAsync {
	void getAllReportConflicts(AsyncCallback<List<ReportConflict>> callback);
	void getReportConflict(Long reportConflictId, AsyncCallback<ReportConflict> callback);
	void saveReportConflict(ReportConflict reportConflict, Long parlamentarianId, AsyncCallback<ReportConflict> callback);
	void deleteReportConflict(Long reportConflictId, AsyncCallback<Void> callback);
}
