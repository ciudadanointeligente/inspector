package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.Society;

import com.google.gwt.view.client.AbstractDataProvider;

public interface BillPresenterIface {
	Long getBillId();
	void setBillId(Long billId);
	Long getParlamentarianId();
	void setParlamentarianId(Long parlamentarianId);
	void showBill();
	void setSelectedParlamentarian(Parlamentarian parlamentarian);
	void showSelectedParlamentarian();
	Parlamentarian getSelectedParlamentarian();
	void loadSelectedParlamentarian();
	void getParlamentarians(Bill bill);
	AbstractDataProvider<Parlamentarian> getParlamentarianData();
	void setParlamentarianData(AbstractDataProvider<Parlamentarian> data);
	void getSocieties(Bill bill);
	AbstractDataProvider<Society> getSocietyData();
	void setSocietyData(AbstractDataProvider<Society> data);
	void initParlamentarianTable();
	void initSocietyTable();
	void showParlamentarianProfile();
}
