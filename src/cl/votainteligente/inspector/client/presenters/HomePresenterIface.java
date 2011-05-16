package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.view.client.AbstractDataProvider;

import java.util.List;

public interface HomePresenterIface {
	void initDataLoad();
	void searchParlamentarian(String keyWord);
	void searchParlamentarian(List<Category> categories);
	void searchCategory(String keyWord);
	void searchCategory(List<Parlamentarian> parlamentarians);
	void searchBill(Parlamentarian parlamentarian, Category category);
	AbstractDataProvider<Parlamentarian> getParlamentarianData();
	void setParlamentarianData(AbstractDataProvider<Parlamentarian> data);
	AbstractDataProvider<Category> getCategoryData();
	void setCategoryData(AbstractDataProvider<Category> data);
	AbstractDataProvider<Bill> getBillData();
	void setBillData(AbstractDataProvider<Bill> data);
	void initParlamentarianTable();
	void initCategoryTable();
	void initBillTable();
	void setBillTable();
	void showParlamentarianProfile();
	void resetSelection();
	void resetSelectionType();
	ApplicationMessages getApplicationMessages();
}
