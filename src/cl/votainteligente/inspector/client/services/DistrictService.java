package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.District;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/district")
public interface DistrictService extends RemoteService {
	List<District> getAllDistricts() throws Exception;
	District getDistrict(Long districtId) throws Exception;
	District saveDistrict(District district) throws Exception;
	void deleteDistrict(District district) throws Exception;
}