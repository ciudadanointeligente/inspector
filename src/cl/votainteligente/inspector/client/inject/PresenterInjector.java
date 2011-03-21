package cl.votainteligente.inspector.client.inject;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(PresenterModule.class)
public interface PresenterInjector extends Ginjector {
	public EventBus getEventBus();
}
