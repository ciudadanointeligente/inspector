package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.services.PartyServiceAsync;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(ServiceModule.class)
public interface ServiceInjector extends Ginjector {
	public PartyServiceAsync getPartyService();
}
