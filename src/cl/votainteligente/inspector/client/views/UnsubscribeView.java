package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.UnsubscribePresenter;
import cl.votainteligente.inspector.client.uihandlers.UnsubscribeUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UnsubscribeView extends ViewWithUiHandlers<UnsubscribeUiHandlers> implements UnsubscribePresenter.MyView {
	private static UnsuscribeViewUiBinder uiBinder = GWT.create(UnsuscribeViewUiBinder.class);
	interface UnsuscribeViewUiBinder extends UiBinder<Widget, UnsubscribeView> {}
	private final Widget widget;

	@UiField Label unsubscribeDetails;
	@UiField Button unsubscribe;

	public UnsubscribeView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setUnsubscribeDetails(String details) {
		unsubscribeDetails.setText(details);
	}

	@UiHandler("unsubscribe")
	void onSubscribeClick(ClickEvent event) {
		getUiHandlers().unsubscribe();
	}
}
