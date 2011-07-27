package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Stock;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/stock")
public interface StockService extends RemoteService {
	List<Stock> getAllStocks() throws Exception;
	Stock getStock(Long stockId) throws Exception;
	Stock saveStock(Stock stock) throws Exception;
	void deleteStock(Stock stock) throws Exception;
	List<Stock> getStocksByBill(Bill bill) throws Exception;
}
