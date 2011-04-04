package cl.votainteligente.inspector.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface ApplicationMessages extends Messages {
	@Key("person.civilStatusSingle")
	public String getCivilStatusSingle();

	@Key("person.civilStatusMarried")
	public String getCivilStatusMarried();

	@Key("person.civilStatusSeparated")
	public String getCivilStatusSeparated();

	@Key("person.civilStatusDivorced")
	public String getCivilStatusDivorced();

	@Key("person.civilStatusWidowed")
	public String getCivilStatusWidowed();
}
