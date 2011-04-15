package cl.votainteligente.inspector.client;

import cl.votainteligente.inspector.client.inject.PresenterInjector;
import cl.votainteligente.inspector.client.inject.ServiceInjector;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

public class Inspector implements EntryPoint {
	private static final PresenterInjector presenterInjector = GWT.create(PresenterInjector.class);
	private static final ServiceInjector serviceInjector = GWT.create(ServiceInjector.class);

	@Override
	public void onModuleLoad() {
		EventBus eventBus = presenterInjector.getEventBus();

		RootPanel.get().add(presenterInjector.getMainPresenter().getDisplay().asWidget());
		RootPanel.get().add(presenterInjector.getPopupPresenter().getDisplay().asWidget());

		PlaceManager placeManager = presenterInjector.getPlaceManager();

		if (History.getToken().isEmpty()) {
			eventBus.fireEvent(new PlaceRequestEvent(new PlaceRequest("home")));
		} else {
			placeManager.fireCurrentPlace();
		}
	}

	public static PresenterInjector getPresenterInjector() {
		return presenterInjector;
	}

	public static ServiceInjector getServiceInjector() {
		return serviceInjector;
	}
}
