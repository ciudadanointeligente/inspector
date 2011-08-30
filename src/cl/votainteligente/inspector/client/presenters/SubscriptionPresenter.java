package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.SubscriberServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.SubscriptionUiHandlers;
import cl.votainteligente.inspector.model.Subscriber;
import cl.votainteligente.inspector.shared.*;

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
		void setEmail(String email);
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
		getView().setEmail("");
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
			NotificationEventParams params = new NotificationEventParams();
			params.setMessage(applicationMessages.getErrorInvalidEmail());
			params.setType(NotificationEventType.ERROR);
			params.setDuration(NotificationEventParams.DURATION_SHORT);
			fireEvent(new NotificationEvent(params));
			return;
		}

		fireEvent(new ShowLoadingEvent());
		subcriberService.getSubscriberByEmail(email, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorSubscriber());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Subscriber result) {
				if (result == null) {
					Subscriber subscriber = new Subscriber();
					subscriber.setEmail(email);
					fireEvent(new ShowLoadingEvent());
					subcriberService.saveSubscriber(subscriber, new AsyncCallback<Subscriber>() {

						@Override
						public void onFailure(Throwable caught) {
							fireEvent(new HideLoadingEvent());
							NotificationEventParams params = new NotificationEventParams();
							params.setMessage(applicationMessages.getErrorSubscriber());
							params.setType(NotificationEventType.ERROR);
							params.setDuration(NotificationEventParams.DURATION_SHORT);
							fireEvent(new NotificationEvent(params));
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
							fireEvent(new HideLoadingEvent());
						}
					});
				} else {
					if (categoryId != null) {
						addCategorySubscription(result.getId());
					} else if (billId != null) {
						addBillSubscription(result.getId());
					}
				}
				fireEvent(new HideLoadingEvent());
			}
		});
	}

	public void addCategorySubscription(Long subscriberId) {
		fireEvent(new ShowLoadingEvent());
		subcriberService.addCategorySubscription(subscriberId, categoryId, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorSubscriber());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Subscriber result) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getSubscriberSuscriptionSuccessful());
				params.setType(NotificationEventType.SUCCESS);
				params.setDuration(NotificationEventParams.DURATION_NORMAL);
				fireEvent(new NotificationEvent(params));
				close();
			}
		});
	}

	public void addBillSubscription(Long subscriberId) {
		fireEvent(new ShowLoadingEvent());
		subcriberService.addCategorySubscription(subscriberId, billId, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorSubscriber());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Subscriber result) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getSubscriberSuscriptionSuccessful());
				params.setType(NotificationEventType.SUCCESS);
				params.setDuration(NotificationEventParams.DURATION_NORMAL);
				fireEvent(new NotificationEvent(params));
				close();
			}
		});
	}
}
