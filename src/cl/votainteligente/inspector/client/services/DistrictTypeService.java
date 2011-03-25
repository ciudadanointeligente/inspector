package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.DistrictType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/districtType")
public interface DistrictTypeService extends RemoteService {
	List<DistrictType> getAllDistrictTypes() throws Exception;
	DistrictType getDistrictType(Long districtTypeId) throws Exception;
	DistrictType saveDistrictType(DistrictType districtType) throws Exception;
	void deleteDistrictType(DistrictType districtType) throws Exception;
}