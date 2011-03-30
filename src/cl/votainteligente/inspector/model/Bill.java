package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;
import java.util.Set;

public class Bill extends LightEntity {
	private Long id;
	private String bulletinNumber;
	private String title;
	private Date entryDate;
	private InitiativeType initiativeType;
	private BillType billType;
	private Chamber originChamber;
	private Urgency urgency;
	private Stage stage;
	private Set<Category> categories;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBulletinNumber() {
		return bulletinNumber;
	}

	public void setBulletinNumber(String bulletinNumber) {
		this.bulletinNumber = bulletinNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public InitiativeType getInitiativeType() {
		return initiativeType;
	}

	public void setInitiativeType(InitiativeType initiativeType) {
		this.initiativeType = initiativeType;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public Chamber getOriginChamber() {
		return originChamber;
	}

	public void setOriginChamber(Chamber originChamber) {
		this.originChamber = originChamber;
	}

	public Urgency getUrgency() {
		return urgency;
	}

	public void setUrgency(Urgency urgency) {
		this.urgency = urgency;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
