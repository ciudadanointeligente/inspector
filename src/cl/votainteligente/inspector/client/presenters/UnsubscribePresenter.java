package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.BillServiceAsync;
import cl.votainteligente.inspector.client.services.CategoryServiceAsync;
import cl.votainteligente.inspector.client.services.SubscriberServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.UnsubscribeUiHandlers;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Subscriber;
import cl.votainteligente.inspector.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import javax.inject.Inject;

public class UnsubscribePresenter extends Presenter<UnsubscribePresenter.MyView, UnsubscribePresenter.MyProxy> implements UnsubscribeUiHandlers {
	public static final String PLACE = "unsubscribe";
	public static final String PARAM_SUBSCRIBER_KEY = "subscriberKey";
	public static final String PARAM_SUBSCRIBER_ID = "subscriberId";
	public static final String PARAM_BILL_ID = "billId";
	public static final String PARAM_CATEGORY_ID = "categoryId";

	public interface MyView extends View, HasUiHandlers<UnsubscribeUiHandlers> {
		void setUnsubscribeDetails(String details);
	}

	public enum UnsubscribeType {
		UNSUBSCRIBE_CATEGORY,
		UNSUBSCRIBE_BILL,
		UNSUBSCRIBE_ALL
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<UnsubscribePresenter> {}

	@Inject
	ApplicationMessages applicationMessages;
	@Inject
	private PlaceManager placeManager;
	@Inject
	SubscriberServiceAsync subcriberService;
	@Inject
	CategoryServiceAsync categoryService;
	@Inject
	BillServiceAsync billService;

	private Long billId;
	private Long categoryId;
	private Long subscriberId;
	private String subscriberKey;
	private Category category;
	private Bill bill;
	private UnsubscribeType unsubscribeType;

	@Inject
	public UnsubscribePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
		if (unsubscribeType.equals(UnsubscribeType.UNSUBSCRIBE_BILL)) {
			getBill();
		} else if (unsubscribeType.equals(UnsubscribeType.UNSUBSCRIBE_CATEGORY)) {
			getCategory();
		} else {
			getView().setUnsubscribeDetails(applicationMessages.getSubscriberUnsubscribeAll());
		}
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		unsubscribeType = null;
		try {
			subscriberId = Long.parseLong(placeRequest.getParameter(PARAM_SUBSCRIBER_ID, null));
			subscriberKey = placeRequest.getParameter(PARAM_SUBSCRIBER_KEY, null);
		} catch (NumberFormatException nfe) {
			subscriberId = null;
			subscriberKey = null;
		}

		try {
			billId = Long.parseLong(placeRequest.getParameter(PARAM_BILL_ID, null));
			unsubscribeType = UnsubscribeType.UNSUBSCRIBE_BILL;
		} catch (NumberFormatException nfe) {
			billId = null;
		}

		try {
			categoryId = Long.parseLong(placeRequest.getParameter(PARAM_CATEGORY_ID, null));
			unsubscribeType = UnsubscribeType.UNSUBSCRIBE_CATEGORY;
		} catch (NumberFormatException nfe) {
			categoryId = null;
		}

		if (unsubscribeType == null) {
			unsubscribeType = UnsubscribeType.UNSUBSCRIBE_ALL;
		}
	}

	public void getCategory() {
		fireEvent(new ShowLoadingEvent());
		categoryService.getCategory(categoryId, new AsyncCallback<Category>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorCategory());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Category result) {
				category = result;
				if (category != null) {
					getView().setUnsubscribeDetails(applicationMessages.getSubscriberUnsubscribeMessage(applicationMessages.getGeneralCategory(), category.getName()));
				}
				fireEvent(new HideLoadingEvent());
			}
		});
	}

	public void getBill() {
		fireEvent(new ShowLoadingEvent());
		billService.getBill(billId, new AsyncCallback<Bill>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorBill());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Bill result) {
				bill = result;
				if (bill != null) {
					getView().setUnsubscribeDetails(applicationMessages.getSubscriberUnsubscribeMessage(applicationMessages.getGeneralBill(), bill.getTitle()));
				}
				fireEvent(new HideLoadingEvent());
			}
		});
	}

	@Override
	public void unsubscribe() {
		if (subscriberId == null || subscriberKey == null || unsubscribeType == null) {
			NotificationEventParams params = new NotificationEventParams();
			params.setMessage(applicationMessages.getErrorIncorrectParameters());
			params.setType(NotificationEventType.ERROR);
			params.setDuration(NotificationEventParams.DURATION_SHORT);
			fireEvent(new NotificationEvent(params));
			return;
		}

		fireEvent(new ShowLoadingEvent());
		subcriberService.getSubscriber(subscriberId, new AsyncCallback<Subscriber>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorSubscriberLoad());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Subscriber result) {
				if (result != null) {
					if (!result.getSuscriberKey().equals(subscriberKey)) {
						NotificationEventParams params = new NotificationEventParams();
						params.setMessage(applicationMessages.getErrorSubscriberKey());
						params.setType(NotificationEventType.ERROR);
						params.setDuration(NotificationEventParams.DURATION_SHORT);
						fireEvent(new NotificationEvent(params));
						return;
					}

					if (unsubscribeType.equals(UnsubscribeType.UNSUBSCRIBE_BILL)) {
						removeBillSubscription(result.getId());
					} else if (unsubscribeType.equals(UnsubscribeType.UNSUBSCRIBE_CATEGORY)) {
						removeCategorySubscription(result.getId());
					}
					fireEvent(new HideLoadingEvent());
				} else {
					fireEvent(new HideLoadingEvent());
					NotificationEventParams params = new NotificationEventParams();
					params.setMessage(applicationMessages.getErrorSubscriberUnsubscribe());
					params.setType(NotificationEventType.ERROR);
					params.setDuration(NotificationEventParams.DURATION_SHORT);
					fireEvent(new NotificationEvent(params));
				}
			}
		});
	}

	public void removeCategorySubscription(Long subscriberId) {
		fireEvent(new ShowLoadingEvent());
		subcriberService.deleteCategorySubscription(subscriberId, subscriberKey, categoryId, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorSubscriberUnsubscribe());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Boolean result) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getSubscriberUnsubscribeSuccesful());
				params.setType(NotificationEventType.SUCCESS);
				params.setDuration(NotificationEventParams.DURATION_NORMAL);
				fireEvent(new NotificationEvent(params));
				placeManager.revealPlace(new PlaceRequest(HomePresenter.PLACE));
			}
		});
	}

	public void removeBillSubscription(Long subscriberId) {
		fireEvent(new ShowLoadingEvent());
		subcriberService.deleteBillSubscription(subscriberId, subscriberKey, billId, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorSubscriberUnsubscribe());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
			}

			@Override
			public void onSuccess(Boolean result) {
				fireEvent(new HideLoadingEvent());
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getSubscriberUnsubscribeSuccesful());
				params.setType(NotificationEventType.SUCCESS);
				params.setDuration(NotificationEventParams.DURATION_NORMAL);
				fireEvent(new NotificationEvent(params));
				placeManager.revealPlace(new PlaceRequest(HomePresenter.PLACE));
			}
		});
	}
}
