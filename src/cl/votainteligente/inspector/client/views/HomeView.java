package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.HomePresenter;
import cl.votainteligente.inspector.client.presenters.HomePresenter.SelectionType;
import cl.votainteligente.inspector.client.presenters.HomePresenterIface;
import cl.votainteligente.inspector.client.resources.DisplayCellTableResource;
import cl.votainteligente.inspector.client.resources.SearchCellTableResource;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.gwtplatform.mvp.client.ViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
	@UiField HTMLPanel parlamentarianTableContainer;
	@UiField TextBox parlamentarianSearch;
	@UiField Label parlamentarianSearchClear;
	@UiField Label parliamentarianMessage;
	@UiField Label selectionType;
	@UiField HTMLPanel categoryPanel;
	@UiField HTMLPanel categoryTableContainer;
	@UiField TextBox categorySearch;
	@UiField Label categorySearchClear;
	@UiField Label categoryMessage;
	@UiField Image parlamentarianImage;
	@UiField Label parlamentarianDisplay;
	@UiField Label parlamentarianProfileLink;
	@UiField Label categoryDisplay;
	CellTable<Parlamentarian> parlamentarianTable;
	CellTable<Category> categoryTable;
	@UiField HTMLPanel billPanel;
	@UiField Label billMessage;
	CellTable<Bill> billTable;

	private HomePresenterIface presenter;

	public HomeView() {
		widget = uiBinder.createAndBindUi(this);
		ResourceBundle.INSTANCE.HomeView().ensureInjected();
		SearchCellTableResource searchResource = GWT.create(SearchCellTableResource.class);
		DisplayCellTableResource displayResource = GWT.create(DisplayCellTableResource.class);
		parlamentarianTable = new CellTable<Parlamentarian>(15, searchResource);
		categoryTable = new CellTable<Category>(15, searchResource);
		billTable = new CellTable<Bill>(15, displayResource);
		parlamentarianTableContainer.add(parlamentarianTable);
		categoryTableContainer.add(categoryTable);
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
	public String getParlamentarianSearch() {
		return parlamentarianSearch.getText();
	}

	@Override
	public void setParlamentarianSearch(String parlamentarianSearch) {
		this.parlamentarianSearch.setText(parlamentarianSearch);
	}

	@Override
	public String getCategorySearch() {
		return categorySearch.getText();
	}

	@Override
	public void setCategorySearch(String categorySearch) {
		this.categorySearch.setText(categorySearch);
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
	public void setParlamentarianImage(String parlamentarianImage) {
		this.parlamentarianImage.setUrl(parlamentarianImage);
	}

	@Override
	public void setCategoryDisplay(String categoryName) {
		categoryDisplay.setText(categoryName);
	}

	@Override
	public void setSelectedType(SelectionType selectedType) {
		switch (selectedType) {
		case SELECTED_PARLAMENTARIAN:
			selectionType.setStyleName(ResourceBundle.INSTANCE.HomeView().lockedParlamentarian());
			break;
		case SELECTED_CATEGORY:
			selectionType.setStyleName(ResourceBundle.INSTANCE.HomeView().lockedCategory());
			break;
		default:
			selectionType.setStyleName(ResourceBundle.INSTANCE.HomeView().unlocked());
			break;
		}
	}

	@Override
	public void setParlamentarianMessage(String message) {
		parliamentarianMessage.setText(message);
		parliamentarianMessage.setVisible(true);
	}

	@Override
	public void hideParlamentarianMessage() {
		parliamentarianMessage.setVisible(false);
	}

	@Override
	public void setCategoryMessage(String message) {
		categoryMessage.setText(message);
		categoryMessage.setVisible(true);
	}

	@Override
	public void hideCategoryMessage() {
		categoryMessage.setVisible(false);
	}

	@Override
	public void setBillMessage(String message) {
		billMessage.setText(message);
		billMessage.setVisible(true);
	}

	@Override
	public void hideBillMessage() {
		billMessage.setVisible(false);
	}

	@UiHandler("parlamentarianSearch")
	public void onParlamentarianSearchKeyUp(KeyUpEvent event) {
		if (presenter != null) {
			if ((event.getNativeKeyCode() >= 48 && event.getNativeKeyCode() <= 57) ||
				(event.getNativeKeyCode() >= 65 && event.getNativeKeyCode() <= 90) ||
				(event.getNativeKeyCode() >= 97 && event.getNativeKeyCode() <= 122)||
				event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
				presenter.searchParlamentarian(parlamentarianSearch.getText());
				categorySearch.setText(presenter.getApplicationMessages().getCategorySearchMessage());
			}
		}
	}

	@UiHandler("parlamentarianSearch")
	public void onParlamentarianSearchClick(ClickEvent event) {
		if (presenter != null) {
			if (parlamentarianSearch.getText().equals(presenter.getApplicationMessages().getParlamentarianSearchMessage())) {
				parlamentarianSearch.setText("");
			}
		}
	}

	@UiHandler("parlamentarianSearchClear")
	public void onParlamentarianSearchClearClick(ClickEvent event) {
		if (presenter != null) {
				parlamentarianSearch.setText("");
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
				parlamentarianSearch.setText(presenter.getApplicationMessages().getParlamentarianSearchMessage());
			}
		}
	}

	@UiHandler("categorySearch")
	public void onCategorySearchClick(ClickEvent event) {
		if (presenter != null) {
			if (categorySearch.getText().equals(presenter.getApplicationMessages().getCategorySearchMessage())) {
				categorySearch.setText("");
			}
		}
	}

	@UiHandler("categorySearchClear")
	public void onCategorySearchClearClick(ClickEvent event) {
		if (presenter != null) {
				categorySearch.setText("");
		}
	}

	@UiHandler("parlamentarianProfileLink")
	public void onClickParlamentarianProfileLink(ClickEvent event) {
		if (presenter != null) {
			presenter.showParlamentarianProfile();
		}
	}

	@UiHandler("selectionType")
	public void onSelectionTypeClick(ClickEvent event) {
		if (presenter != null) {
			presenter.resetSelectionType();
		}
	}
}
