package cl.votainteligente.inspector.client.places;

import cl.votainteligente.inspector.client.presenters.HomePresenter;

import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class MyPlaceManager extends PlaceManagerImpl {

	@Inject
	public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
		super(eventBus, tokenFormatter);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(new PlaceRequest(HomePresenter.PLACE));
	}

}
