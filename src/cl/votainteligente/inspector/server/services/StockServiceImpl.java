package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.StockService;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Stock;

import org.hibernate.*;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class StockServiceImpl implements StockService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Stock> getAllStocks() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Stock.class);
			List<Stock> stocks = criteria.list();
			hibernate.getTransaction().commit();
			return stocks;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Stock getStock(Long stockId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Stock stock = (Stock) hibernate.get(Stock.class, stockId);
			Hibernate.initialize(stock.getCategories());
			hibernate.getTransaction().commit();
			return stock;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Stock saveStock(Stock stock) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(stock);
			hibernate.getTransaction().commit();
			return stock;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteStock(Stock stock) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(stock);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public List<Stock> getStocksByBill(Bill bill) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			bill = (Bill) hibernate.load(Bill.class, bill.getId());

			Criteria criteria = hibernate.createCriteria(Stock.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("categories", FetchMode.JOIN);
			criteria.createAlias("categories", "c");

			Disjunction categoriesFilter = Restrictions.disjunction();

			for (Category category : bill.getCategories()) {
				categoriesFilter.add(Restrictions.eq("c.id", category.getId()));
			}

			criteria.add(categoriesFilter);
			List<Stock> stocks = criteria.list();

			for (Stock stock : stocks) {
				Hibernate.initialize(stock.getCategories());
			}
			hibernate.getTransaction().commit();
			return stocks;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
