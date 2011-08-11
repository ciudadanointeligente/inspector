package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.StockPresenter;
import cl.votainteligente.inspector.client.uihandlers.StockUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StockView extends ViewWithUiHandlers<StockUiHandlers> implements StockPresenter.MyView {
	private static StockViewUiBinder uiBinder = GWT.create(StockViewUiBinder.class);
	interface StockViewUiBinder extends UiBinder<Widget, StockView> {}
	private final Widget widget;

	@UiField Label stockName;
	@UiField Label stockFantasyName;
	@UiField Label stockInitialQuantity;
	@UiField Label parlamentarianStock;
	@UiField Label stockUnit;
	@UiField Label stockEmissionDate;
	@UiField Label stockRemark;
	@UiField Label close;

	public StockView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void clearStockData() {
		stockName.setText("");
		stockFantasyName.setText("");
		stockInitialQuantity.setText("");
		parlamentarianStock.setText("");
		stockUnit.setText("");
		stockEmissionDate.setText("");
		stockRemark.setText("");
	}

	@Override
	public void setStockName(String stockName) {
		this.stockName.setText(stockName);
	}

	@Override
	public void setStockFantasyName(String stockFantasyName) {
		this.stockFantasyName.setText(stockFantasyName);
	}

	@Override
	public void setStockInitialQuantity(String stockInitialQuantity) {
		this.stockInitialQuantity.setText(stockInitialQuantity);
	}

	@Override
	public void setParlamentarianStock(String parlamentarianStock) {
		this.parlamentarianStock.setText(parlamentarianStock);
	}

	@Override
	public void setStockUnit(String stockUnit) {
		this.stockUnit.setText(stockUnit);
	}

	@Override
	public void setStockEmissionDate(String stockEmissionDate) {
		this.stockEmissionDate.setText(stockEmissionDate);
	}

	@Override
	public void setStockRemark(String stockRemark) {
		this.stockRemark.setText(stockRemark);
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		getUiHandlers().close();
	}
}
