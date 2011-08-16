package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;
import java.util.Set;

public class Stock extends LightEntity implements Comparable<Stock> {
	private Long id;
	private Date emissionDate;
	private String remark;
	private String unit;
	private String name;
	private String fantasyName;
	private String classification;
	private Long initialQuantity;
	private Long totalEquivalentAmount;
	private Set<Category> categories;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public Long getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(Long initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public Long getTotalEquivalentAmount() {
		return totalEquivalentAmount;
	}

	public void setTotalEquivalentAmount(Long totalEquivalentAmount) {
		this.totalEquivalentAmount = totalEquivalentAmount;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@Override
	public int compareTo(Stock obj) {
		return (obj == null || obj.getName() == null) ? -1 : -obj.getName().compareTo(name);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (!(other instanceof Stock)) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		return getId().equals(((Stock) other).getId());
	}

	@Override
	public int hashCode() {
			return this.id.hashCode();
	}
}
