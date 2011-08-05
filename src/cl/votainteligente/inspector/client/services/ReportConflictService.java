package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.ReportConflict;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/reportConflict")
public interface ReportConflictService extends RemoteService {
	List<ReportConflict> getAllReportConflicts() throws Exception;
	ReportConflict getReportConflict(Long reportConflictId) throws Exception;
	ReportConflict saveReportConflict(ReportConflict reportConflict, Long parlamentarianId) throws Exception;
	void deleteReportConflict(Long reportConflictId) throws Exception;
}
