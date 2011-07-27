package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Stock;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface StockServiceAsync {
	void getAllStocks(AsyncCallback<List<Stock>> callback);
	void getStock(Long stockId, AsyncCallback<Stock> callback);
	void saveStock(Stock stock, AsyncCallback<Stock> callback);
	void deleteStock(Stock stock, AsyncCallback<Void> callback);
	void getStocksByBill(Bill bill, AsyncCallback<List<Stock>> callback);
}
