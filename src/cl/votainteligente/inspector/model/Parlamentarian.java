package cl.votainteligente.inspector.model;

import com.google.gwt.view.client.ProvidesKey;

import java.util.Map;
import java.util.Set;

public class Parlamentarian extends Person implements Comparable<Parlamentarian> {
	private ParlamentarianType parlamentarianType;
	private Set<Commission> permanentCommissions;
	private Set<Commission> specialCommissions;
	private Party party;
	private District district;
	private Boolean active;
	private Map<Society, Boolean> societies;
	private Set<Bill> authoredBills;
	private Set<Bill> votedBills;
	private String interestDeclarationFile;
	private String patrimonyDeclarationFile;
	private String image;

	public static final ProvidesKey<Parlamentarian> KEY_PROVIDER = new ProvidesKey<Parlamentarian>() {
		public Object getKey(Parlamentarian parlamentarian) {
			return parlamentarian == null ? null : parlamentarian.getId();
		}
	};

	public ParlamentarianType getParlamentarianType() {
		return parlamentarianType;
	}

	public void setParlamentarianType(ParlamentarianType parlamentarianType) {
		this.parlamentarianType = parlamentarianType;
	}

	public Set<Commission> getPermanentCommissions() {
		return permanentCommissions;
	}

	public void setPermanentCommissions(Set<Commission> permanentCommissions) {
		this.permanentCommissions = permanentCommissions;
	}

	public Set<Commission> getSpecialCommissions() {
		return specialCommissions;
	}

	public void setSpecialCommissions(Set<Commission> specialCommissions) {
		this.specialCommissions = specialCommissions;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Map<Society, Boolean> getSocieties() {
		return societies;
	}

	public void setSocieties(Map<Society, Boolean> societies) {
		this.societies = societies;
	}

	public Set<Bill> getAuthoredBills() {
		return authoredBills;
	}

	public void setAuthoredBills(Set<Bill> authoredBills) {
		this.authoredBills = authoredBills;
	}

	public Set<Bill> getVotedBills() {
		return votedBills;
	}

	public void setVotedBills(Set<Bill> votedBills) {
		this.votedBills = votedBills;
	}

	public String getInterestDeclarationFile() {
		return interestDeclarationFile;
	}

	public void setInterestDeclarationFile(String interestDeclarationFile) {
		this.interestDeclarationFile = interestDeclarationFile;
	}

	public String getPatrimonyDeclarationFile() {
		return patrimonyDeclarationFile;
	}

	public void setPatrimonyDeclarationFile(String patrimonyDeclarationFile) {
		this.patrimonyDeclarationFile = patrimonyDeclarationFile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int compareTo(Parlamentarian obj) {
		return (obj == null || obj.getLastName() == null) ? -1 : -obj.getLastName().compareTo(this.getLastName());
	}
}
