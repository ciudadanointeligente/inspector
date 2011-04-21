package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.PopupPresenter;

import com.gwtplatform.mvp.client.ViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;

public class PopupView extends ViewImpl implements PopupPresenter.MyView {
	private static PopupViewUiBinder uiBinder = GWT.create(PopupViewUiBinder.class);
	interface PopupViewUiBinder extends UiBinder<Widget, PopupView> {}
	private final Widget widget;

	@UiField PopupPanel popup;
	@UiField FlowPanel layout;
	@UiField Button close;

	public PopupView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (PopupPresenter.TYPE_POPUP_CONTENT.equals(slot)) {
			layout.clear();

			if (content != null) {
				layout.add(content);
				popup.center();
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@UiHandler("close")
	public void onCloseClick(ClickEvent event) {
		History.back();
	}

}
