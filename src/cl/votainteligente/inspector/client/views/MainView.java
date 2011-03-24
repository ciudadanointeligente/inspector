package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.MainPresenter;
import cl.votainteligente.inspector.client.presenters.MainPresenterIface;

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

	MainPresenterIface presenter;

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(MainPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public FlowPanel getLayout() {
		return mainPanel;
	}
}
