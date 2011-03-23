package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SocietyType extends LightEntity {
	private Long id;
	private Long name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}
}
