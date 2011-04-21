package cl.votainteligente.inspector.client;

import cl.votainteligente.inspector.client.inject.MyInjector;

import com.gwtplatform.mvp.client.DelayedBindRegistry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Inspector implements EntryPoint {
	public final MyInjector ginjector = GWT.create(MyInjector.class);

	@Override
	public void onModuleLoad() {
		DelayedBindRegistry.bind(ginjector);
		ginjector.getPlaceManager().revealCurrentPlace();
	}
}
