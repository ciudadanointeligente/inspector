package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.services.PartyServiceAsync;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class ServiceModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(PartyServiceAsync.class).in(Singleton.class);
	}
}
