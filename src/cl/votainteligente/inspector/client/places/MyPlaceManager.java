package cl.votainteligente.inspector.client.places;

import cl.votainteligente.inspector.client.GoogleAnalytics;
import cl.votainteligente.inspector.client.presenters.HomePresenter;

import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class MyPlaceManager extends PlaceManagerImpl implements NavigationHandler {

	@Inject
	public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
		super(eventBus, tokenFormatter);
		eventBus.addHandler (NavigationEvent.getType (), this);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(new PlaceRequest(HomePresenter.PLACE));
	}

	@Override
	public void onNavigation(NavigationEvent navigationEvent) {
		String historyToken = "Inspector/#" + buildHistoryToken(navigationEvent.getRequest());
		GoogleAnalytics.trackHit(historyToken);
	}
}
