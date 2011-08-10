package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;

public class ReportConflict extends LightEntity implements Comparable<ReportConflict> {
	private Long id;
	private Parlamentarian parlamentarian;
	private Date submitDate;
	private String report;

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

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date reportDate) {
		this.submitDate = reportDate;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	@Override
	public int compareTo(ReportConflict obj) {
		return (obj == null || obj.getSubmitDate() == null) ? -1 : -obj.getSubmitDate().compareTo(submitDate);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (!(other instanceof ReportConflict)) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		return getId().equals(((ReportConflict) other).getId());
	}
}
