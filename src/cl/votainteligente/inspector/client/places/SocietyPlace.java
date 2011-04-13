package cl.votainteligente.inspector.client.places;

import cl.votainteligente.inspector.client.presenters.SocietyPresenter;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SocietyPlace extends ProvidedPresenterPlace<SocietyPresenter> {

	public static final String NAME = "society";
	public static final String PARAM_SOCIETY_ID = "societyId";

	@Inject
	public SocietyPlace(Provider<SocietyPresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void preparePresenter(PlaceRequest request, SocietyPresenter presenter) {
		try {
			Long SocietyId = Long.parseLong(request.getParameter(PARAM_SOCIETY_ID, null));
			presenter.setSocietyId(SocietyId);
		} catch (NumberFormatException nfe) {
			presenter.setSocietyId(null);
		}
	}

	@Override
	protected PlaceRequest prepareRequest(PlaceRequest request, SocietyPresenter presenter) {
		if (presenter.getSocietyId() != null) {
			request = request.with(PARAM_SOCIETY_ID, presenter.getSocietyId().toString());
		}

		return request;
	}

}
