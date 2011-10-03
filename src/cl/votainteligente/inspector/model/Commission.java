package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Commission extends LightEntity implements Comparable<Commission> {
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

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(Commission obj) {
		return (obj == null || obj.getName() == null) ? -1 : -obj.getName().compareTo(this.getName());
	}
}
