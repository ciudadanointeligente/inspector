package cl.votainteligente.inspector.client.places;

import cl.votainteligente.inspector.client.presenters.ParlamentarianPresenter;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ParlamentarianPlace extends ProvidedPresenterPlace<ParlamentarianPresenter> {

	public static final String NAME = "parlamentarian";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";

	@Inject
	public ParlamentarianPlace(Provider<ParlamentarianPresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void preparePresenter(PlaceRequest request, ParlamentarianPresenter presenter) {
		try {
			Long parlamentarianId = Long.parseLong(request.getParameter(PARAM_PARLAMENTARIAN_ID, null));
			presenter.setParlamentarianId(parlamentarianId);
		} catch (NumberFormatException nfe) {
			presenter.setParlamentarianId(null);
		}
	}

	@Override
	protected PlaceRequest prepareRequest(PlaceRequest request, ParlamentarianPresenter presenter) {
		if (presenter.getParlamentarianId() != null) {
			request = request.with(PARAM_PARLAMENTARIAN_ID, presenter.getParlamentarianId().toString());
		}

		return request;
	}

}
