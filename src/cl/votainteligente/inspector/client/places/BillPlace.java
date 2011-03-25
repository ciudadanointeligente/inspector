package cl.votainteligente.inspector.client.places;

import cl.votainteligente.inspector.client.presenters.BillPresenter;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BillPlace extends ProvidedPresenterPlace<BillPresenter> {

	public static final String NAME = "bill";
	public static final String PARAM_BILL_ID = "billId";

	@Inject
	public BillPlace(Provider<BillPresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void preparePresenter(PlaceRequest request, BillPresenter presenter) {
		try {
			Long billId = Long.parseLong(request.getParameter(PARAM_BILL_ID, null));
			presenter.setBillId(billId);
		} catch (NumberFormatException nfe) {
			presenter.setBillId(null);
		}
	}

	@Override
	protected PlaceRequest prepareRequest(PlaceRequest request, BillPresenter presenter) {
		if (presenter.getBillId() != null) {
			request = request.with(PARAM_BILL_ID, presenter.getBillId().toString());
		}

		return request;
	}

}
