package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.PopupPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;

public class PopupView extends Composite implements PopupPresenter.Display {

	private static PopupViewUiBinder uiBinder = GWT.create(PopupViewUiBinder.class);

	interface PopupViewUiBinder extends UiBinder<Widget, PopupView> {
	}

	@UiField PopupPanel popup;
	@UiField FlowPanel layout;
	@UiField Button close;

	public PopupView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addWidget(Widget widget) {
	}

	@Override
	public void removeWidget(Widget widget) {
	}

	@Override
	public void showWidget(Widget widget) {
		layout.clear();
		layout.add(widget);
		popup.center();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@UiHandler("close")
	public void onCloseClick(ClickEvent event) {
		History.back();
	}

}
