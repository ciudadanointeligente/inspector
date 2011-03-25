package cl.votainteligente.inspector.client.places;

import cl.votainteligente.inspector.client.presenters.HomePresenter;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HomePlace extends ProvidedPresenterPlace<HomePresenter> {

	public static final String NAME = "home";

	@Inject
	public HomePlace(Provider<HomePresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

}
