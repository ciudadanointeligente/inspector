package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.uihandlers.MainUiHandlers;
import cl.votainteligente.inspector.shared.NotificationEvent;
import cl.votainteligente.inspector.shared.NotificationEventHandler;
import cl.votainteligente.inspector.shared.NotificationEventParams;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;

public class MainPresenter extends Presenter<MainPresenter.MyView, MainPresenter.MyProxy> implements MainUiHandlers, NotificationEventHandler {
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_MAIN_CONTENT = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_POPUP_CONTENT = new Type<RevealContentHandler<?>>();

	public interface MyView extends View, HasUiHandlers<MainUiHandlers> {
		void setNotificationMessage(NotificationEventParams params);
		void clearNotifications();
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<MainPresenter> {
	}

	@Inject
	public MainPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		addHandler(NotificationEvent.TYPE, this);
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealRootContentEvent(this));
	}

	@Override
	public void clearPopupSlot() {
		clearSlot(SLOT_POPUP_CONTENT);
	}

	@Override
	public void onNotification(NotificationEvent notificationEvent) {
		if (notificationEvent.getParams() != null) {
			getView().setNotificationMessage(notificationEvent.getParams());
		}
	}

	public void clearNotifications() {
		getView().clearNotifications();
	}
}
