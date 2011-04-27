package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.HomePresenter;
import cl.votainteligente.inspector.client.presenters.HomePresenterIface;
import cl.votainteligente.inspector.client.resources.DisplayCellTableResource;
import cl.votainteligente.inspector.client.resources.SearchCellTableResource;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.gwtplatform.mvp.client.ViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.*;

public class HomeView extends ViewImpl implements HomePresenter.MyView {
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {}
	private final Widget widget;

	@UiField HTMLPanel parlamentarianPanel;
	@UiField TextBox parlamentarianSearch;
	@UiField HTMLPanel categoryPanel;
	@UiField TextBox categorySearch;
	@UiField Label parlamentarianDisplay;
	@UiField Label categoryDisplay;
	CellTable<Parlamentarian> parlamentarianTable;
	CellTable<Category> categoryTable;
	@UiField HTMLPanel billPanel;
	CellTable<Bill> billTable;

	private HomePresenterIface presenter;

	public HomeView() {
		widget = uiBinder.createAndBindUi(this);
		SearchCellTableResource searchResource = GWT.create(SearchCellTableResource.class);
		DisplayCellTableResource displayResource = GWT.create(DisplayCellTableResource.class);
		parlamentarianTable = new CellTable<Parlamentarian>(15, searchResource);
		categoryTable = new CellTable<Category>(15, searchResource);
		billTable = new CellTable<Bill>(15, displayResource);
		parlamentarianPanel.add(parlamentarianTable);
		categoryPanel.add(categoryTable);
		billPanel.add(billTable);
	}

	@Override
	public Widget asWidget() {
		return widget;
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
