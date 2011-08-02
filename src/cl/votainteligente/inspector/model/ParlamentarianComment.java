package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;

public class ParlamentarianComment extends LightEntity implements Comparable<ParlamentarianComment> {
	private Long id;
	private Parlamentarian parlamentarian;
	private String key;
	private String subject;
	private String body;
	private Date creationDate;
	private Boolean aproved;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Parlamentarian getParlamentarian() {
		return parlamentarian;
	}

	public void setParlamentarian(Parlamentarian parlamentarian) {
		this.parlamentarian = parlamentarian;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getAproved() {
		return aproved;
	}

	public void setAproved(Boolean aproved) {
		this.aproved = aproved;
	}

	@Override
	public int compareTo(ParlamentarianComment obj) {
		return (obj == null || obj.getCreationDate() == null) ? -1 : -obj.getCreationDate().compareTo(creationDate);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (!(other instanceof Society)) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		return getId().equals(((Society) other).getId());
	}
}
