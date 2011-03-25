package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.DistrictType;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface DistrictTypeServiceAsync {
	void getAllDistrictTypes(AsyncCallback<List<DistrictType>> callback);
	void getDistrictType(Long districtTypeId, AsyncCallback<DistrictType> callback);
	void saveDistrictType(DistrictType districtType, AsyncCallback<DistrictType> callback);
	void deleteDistrictType(DistrictType districtType, AsyncCallback<Void> callback);
}
