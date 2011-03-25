package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.MainPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite implements MainPresenter.Display {
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}

	@UiField FlowPanel mainPanel;

	public MainView() {
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
		mainPanel.clear();
		mainPanel.add(widget);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public FlowPanel getLayout() {
		return mainPanel;
	}
}
