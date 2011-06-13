package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.SubscriberServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.PermalinkUiHandlers;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

import javax.inject.Inject;

public class PermalinkPresenter extends Presenter<PermalinkPresenter.MyView, PermalinkPresenter.MyProxy> implements PermalinkUiHandlers {
	public static final String PLACE = "permalink";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";
	public static final String PARAM_CATEGORY_ID = "categoryId";
	public static final String PARAM_PLACE_TOKEN = "placeToken";

	public interface MyView extends View, HasUiHandlers<PermalinkUiHandlers> {
		void setPermalink(String permalink);
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<PermalinkPresenter> {}

	private Long parlamentarianId;
	private Long categoryId;
	private String placeToken;
	private String email;

	@Inject
	ApplicationMessages applicationMessages;
	@Inject
	SubscriberServiceAsync subcriberService;
	@Inject
	PlaceManager placeManager;

	@Inject
	public PermalinkPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
		getPermalink();
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_POPUP_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		try {
			placeToken = placeRequest.getParameter(PARAM_PLACE_TOKEN, null);
		} catch (NumberFormatException nfe) {
			placeToken = null;
		}

		try {
			parlamentarianId = Long.parseLong(placeRequest.getParameter(PARAM_PARLAMENTARIAN_ID, null));
		} catch (NumberFormatException nfe) {
			parlamentarianId = null;
		}

		try {
			categoryId = Long.parseLong(placeRequest.getParameter(PARAM_CATEGORY_ID, null));
		} catch (NumberFormatException nfe) {
			categoryId = null;
		}
	}

	public void getPermalink() {
		PlaceRequest placeRequest = new PlaceRequest(placeToken)
			.with(HomePresenter.PARAM_PARLAMENTARIAN_ID, parlamentarianId.toString())
			.with(HomePresenter.PARAM_CATEGORY_ID, categoryId.toString());
		getView().setPermalink(Window.Location.createUrlBuilder().setHash(placeManager.buildHistoryToken(placeRequest)).buildString());
	}

	@Override
	public void close() {
		History.back();
	}
}
