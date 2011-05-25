package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.MainPresenter;

import com.gwtplatform.mvp.client.ViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends ViewImpl implements MainPresenter.MyView {
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}
	private final Widget widget;

	@UiField FlowPanel mainPanel;

	public MainView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (MainPresenter.SLOT_MAIN_CONTENT.equals(slot)) {
			mainPanel.clear();

			if (content != null) {
				mainPanel.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
