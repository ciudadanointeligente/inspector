package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.SubscriberServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.SubscriptionUiHandlers;
import cl.votainteligente.inspector.model.Subscriber;
import cl.votainteligente.inspector.shared.Validator;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

import javax.inject.Inject;

public class SubscriptionPresenter extends Presenter<SubscriptionPresenter.MyView, SubscriptionPresenter.MyProxy> implements SubscriptionUiHandlers {
	public static final String PLACE = "subscription";
	public static final String PARAM_BILL_ID = "billId";
	public static final String PARAM_CATEGORY_ID = "categoryId";

	public interface MyView extends View, HasUiHandlers<SubscriptionUiHandlers> {
		String getEmail();
		void setNotification(String message);
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<SubscriptionPresenter> {}

	private Long billId;
	private Long categoryId;
	private String email;

	@Inject
	ApplicationMessages applicationMessages;
	@Inject
	SubscriberServiceAsync subcriberService;

	@Inject
	public SubscriptionPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_POPUP_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		try {
			billId = Long.parseLong(placeRequest.getParameter(PARAM_BILL_ID, null));
		} catch (NumberFormatException nfe) {
			billId = null;
		}

		try {
			categoryId = Long.parseLong(placeRequest.getParameter(PARAM_CATEGORY_ID, null));
		} catch (NumberFormatException nfe) {
			categoryId = null;
		}
	}

	@Override
	public void close() {
		History.back();
	}

	@Override
	public void subscribe() {
		email = getView().getEmail();

		if (!Validator.validateEmail(email)) {
			getView().setNotification(applicationMessages.getErrorInvalidEmail());
			return;
		}

		subcriberService.getSubscriberByEmail(email, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().setNotification(applicationMessages.getErrorSubscriber());
			}

			@Override
			public void onSuccess(Subscriber result) {
				if (result == null) {
					Subscriber subscriber = new Subscriber();
					subscriber.setEmail(email);
					subcriberService.saveSubscriber(subscriber, new AsyncCallback<Subscriber>() {

						@Override
						public void onFailure(Throwable caught) {
							getView().setNotification(applicationMessages.getErrorSubscriber());
						}

						@Override
						public void onSuccess(Subscriber result) {
							if (result != null) {
								if (categoryId != null) {
									addCategorySubscription(result.getId());
								} else if (billId != null) {
									addBillSubscription(result.getId());
								}
							}
						}
					});
				} else {
					if (categoryId != null) {
						addCategorySubscription(result.getId());
					} else if (billId != null) {
						addBillSubscription(result.getId());
					}
				}
			}
		});
	}

	public void addCategorySubscription(Long subscriberId) {
		subcriberService.addCategorySubscription(subscriberId, categoryId, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().setNotification(applicationMessages.getErrorSubscriber());
			}

			@Override
			public void onSuccess(Subscriber result) {
				getView().setNotification(applicationMessages.getSubscriberSuscriptionSuccessful());
			}
		});
	}

	public void addBillSubscription(Long subscriberId) {
		subcriberService.addCategorySubscription(subscriberId, billId, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().setNotification(applicationMessages.getErrorSubscriber());
			}

			@Override
			public void onSuccess(Subscriber result) {
				getView().setNotification(applicationMessages.getSubscriberSuscriptionSuccessful());
			}
		});
	}
}