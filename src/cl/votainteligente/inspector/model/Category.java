package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Set;

public class Category extends LightEntity {
	private Long id;
	private String name;
	private Set<Category> relatedCategories;

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

	public Set<Category> getRelatedCategories() {
		return relatedCategories;
	}

	public void setRelatedCategories(Set<Category> relatedCategories) {
		this.relatedCategories = relatedCategories;
	}
}
