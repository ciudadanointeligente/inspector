package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.BillPresenter;
import cl.votainteligente.inspector.client.presenters.BillPresenterIface;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.Society;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class BillView extends Composite implements BillPresenter.Display {
	private static BillViewUiBinder uiBinder = GWT.create(BillViewUiBinder.class);
	interface BillViewUiBinder extends UiBinder<Widget, BillView> {}

	@UiField Label billName;
	@UiField Label billDate;
	@UiField Label billVote;
	@UiField Label billStatus;
	@UiField CellTable<Parlamentarian> parlamentarianTable;
	@UiField CellTable<Society> societyTable;

	BillPresenterIface presenter;

	public BillView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(BillPresenterIface presenter) {
		this.presenter = presenter;
	}
}
