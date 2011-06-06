package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.SubscriptionPresenter;
import cl.votainteligente.inspector.client.uihandlers.SubscriptionUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class SubscriptionView extends ViewWithUiHandlers<SubscriptionUiHandlers> implements SubscriptionPresenter.MyView {
	private static SubscriptionViewUiBinder uiBinder = GWT.create(SubscriptionViewUiBinder.class);
	interface SubscriptionViewUiBinder extends UiBinder<Widget, SubscriptionView> {}
	private final Widget widget;

	@UiField FocusPanel subscriptionPanel;
	@UiField Label close;
	@UiField Label notification;
	@UiField TextBox email;
	@UiField Button subscribe;

	public SubscriptionView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public String getEmail() {
		return email.getText();
	}

	@Override
	public void setEmail(String email) {
		this.email.setText(email);
	}

	@Override
	public void setNotification(String message) {
		Window.alert(message);
	}

	@UiHandler("subscriptionPanel")
	void onSubscriptionPanelKeyDown(KeyDownEvent key) {
		if (key.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			getUiHandlers().close();
		}
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		getUiHandlers().close();
	}

	@UiHandler("subscribe")
	void onSubscribeClick(ClickEvent event) {
		getUiHandlers().subscribe();
	}
}
