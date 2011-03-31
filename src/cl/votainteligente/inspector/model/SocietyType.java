package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SocietyType extends LightEntity {
	private Long id;
	private String name;

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
}
