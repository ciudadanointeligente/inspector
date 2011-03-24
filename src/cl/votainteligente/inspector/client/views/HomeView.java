package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.HomePresenter;
import cl.votainteligente.inspector.client.presenters.HomePresenterIface;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class HomeView extends Composite implements HomePresenter.Display {
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {}

	@UiField CellTable<Parlamentarian> parlamentarianTable;
	@UiField CellTable<Category> categoryTable;
	@UiField CellTable<Bill> billTable;
	@UiField TextBox parlamentarianSearch;
	@UiField TextBox categorySearch;

	private HomePresenterIface presenter;

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(HomePresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setParlamentaryData(ListDataProvider<Parlamentarian> data) {
		data.addDataDisplay(parlamentarianTable);
	}

	@Override
	public void setCategoryData(ListDataProvider<Category> data) {
		data.addDataDisplay(categoryTable);
	}

	@Override
	public void setBillData(ListDataProvider<Bill> data) {
		data.addDataDisplay(billTable);
	}

	@Override
	public void getParlamentarianSearch() {
		parlamentarianSearch.getText();
	}

	@Override
	public void getCategorySearch() {
		categorySearch.getText();
	}
}
