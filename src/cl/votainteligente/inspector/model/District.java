package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class District extends LightEntity {
	private Long id;
	private String name;
	private DistrictType districtType;
	private District parentDistrict;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DistrictType getDistrictType() {
		return districtType;
	}

	public void setDistrictType(DistrictType districtType) {
		this.districtType = districtType;
	}

	public District getParentDistrict() {
		return parentDistrict;
	}

	public void setParentDistrict(District parentDistrict) {
		this.parentDistrict = parentDistrict;
	}
}
