package cl.votainteligente.inspector.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Map;

public class Subscriber extends LightEntity implements Comparable<Subscriber> {
	private Long id;
	private String email;
	private String suscriberKey;
	private Map<Bill, Integer> subscriptions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSuscriberKey() {
		return suscriberKey;
	}

	public void setSuscriberKey(String suscriberKey) {
		this.suscriberKey = suscriberKey;
	}

	public Map<Bill, Integer> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Map<Bill, Integer> subscriptions) {
		this.subscriptions = subscriptions;
	}

	@Override
	public int compareTo(Subscriber obj) {
		return (obj == null || obj.getEmail() == null) ? -1 : -obj.getEmail().compareTo(email);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (!(other instanceof Subscriber)) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		return getId().equals(((Subscriber) other).getId());
	}

	@Override
	public int hashCode() {
			return (this.id == null)? 0 : this.id.hashCode();
	}
}
