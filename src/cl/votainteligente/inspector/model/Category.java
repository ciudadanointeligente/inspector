package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import com.google.gwt.view.client.ProvidesKey;

import java.util.Set;

public class Category extends LightEntity implements Comparable<Category> {
	private Long id;
	private String name;
	private Set<Category> relatedCategories;

	public static final ProvidesKey<Category> KEY_PROVIDER = new ProvidesKey<Category>() {
		public Object getKey(Category category) {
			return category == null ? null : category.getId();
		}
	};

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

	@Override
	public int compareTo(Category obj) {
		return (obj == null || obj.getName() == null) ? -1 : -obj.getName().compareTo(name);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (!(other instanceof Category)) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		return getId().equals(((Category) other).getId());
	}
}
