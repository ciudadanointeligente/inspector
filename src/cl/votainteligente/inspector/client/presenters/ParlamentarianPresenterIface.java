package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;

public interface ParlamentarianPresenterIface {
	Long getParlamentarianId();
	void setParlamentarianId(Long parlamentarianId);
	ApplicationMessages getApplicationMessages();
	void getParlamentarian(Long parlamentarianId);
}
