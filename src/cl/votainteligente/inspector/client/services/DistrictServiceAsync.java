package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.District;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface DistrictServiceAsync {
	void getAllDistricts(AsyncCallback<List<District>> callback);
	void getDistrict(Long districtId, AsyncCallback<District> callback);
	void saveDistrict(District district, AsyncCallback<District> callback);
	void deleteDistrict(District district, AsyncCallback<Void> callback);
}
