package cl.votainteligente.inspector.client.inject;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

import com.google.inject.Singleton;

public class PresenterModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
	}
}
