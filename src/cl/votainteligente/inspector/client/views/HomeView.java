package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.HomePresenter;
import cl.votainteligente.inspector.client.presenters.HomePresenterIface;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.*;

public class HomeView extends Composite implements HomePresenter.Display {
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	@UiField TextBox parlamentarianSearch;
	@UiField CellTable<Parlamentarian> parlamentarianTable;
	@UiField TextBox categorySearch;
	@UiField CellTable<Category> categoryTable;
	@UiField Label parlamentarianDisplay;
	@UiField Label categoryDisplay;
	@UiField CellTable<Bill> billTable;

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
	public void getParlamentarianSearch() {
		parlamentarianSearch.getText();
	}

	@Override
	public void getCategorySearch() {
		categorySearch.getText();
	}

	@Override
	public CellTable<Parlamentarian> getParlamentarianTable() {
		return parlamentarianTable;
	}

	@Override
	public CellTable<Category> getCategoryTable() {
		return categoryTable;
	}

	@Override
	public CellTable<Bill> getBillTable() {
		return billTable;
	}

	@Override
	public void setParlamentarianDisplay(String parlamentarianName) {
		parlamentarianDisplay.setText(parlamentarianName);
	}

	@Override
	public void setCategoryDisplay(String categoryName) {
		categoryDisplay.setText(categoryName);
	}

	@UiHandler("parlamentarianSearch")
	public void onParlamentarianSearchKeyUp(KeyUpEvent event) {
		if (presenter != null) {
			if ((event.getNativeKeyCode() >= 48 && event.getNativeKeyCode() <= 57) ||
				(event.getNativeKeyCode() >= 65 && event.getNativeKeyCode() <= 90) ||
				(event.getNativeKeyCode() >= 97 && event.getNativeKeyCode() <= 122)||
				event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
				presenter.searchParlamentarian(parlamentarianSearch.getText());
			}
		}
	}

	@UiHandler("categorySearch")
	public void onCategorySearchKeyUp(KeyUpEvent event) {
		if (presenter != null) {
			if ((event.getNativeKeyCode() >= 48 && event.getNativeKeyCode() <= 57) ||
				(event.getNativeKeyCode() >= 65 && event.getNativeKeyCode() <= 90) ||
				(event.getNativeKeyCode() >= 97 && event.getNativeKeyCode() <= 122)||
				event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
				presenter.searchCategory(categorySearch.getText());
			}
		}
	}
}
