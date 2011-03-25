package cl.votainteligente.inspector.client.places;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.DefaultPlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;

import com.google.inject.Inject;

public class BasicPlaceManager extends DefaultPlaceManager {

	@Inject
	public BasicPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, HomePlace homePlace, BillPlace billPlace) {
		super(eventBus, tokenFormatter, homePlace, billPlace);
	}

}
