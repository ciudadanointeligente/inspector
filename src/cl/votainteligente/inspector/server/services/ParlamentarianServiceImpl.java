package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.ParlamentarianService;
import cl.votainteligente.inspector.model.*;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class ParlamentarianServiceImpl implements ParlamentarianService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Parlamentarian> getAllParlamentarians() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Parlamentarian.class);
			criteria.addOrder(Order.asc("lastName"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("party", FetchMode.JOIN);
			List<Parlamentarian> parlamentarians = criteria.list();
			hibernate.getTransaction().commit();
			return parlamentarians;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Parlamentarian getParlamentarian(Long parlamentarianId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.get(Parlamentarian.class, parlamentarianId);

			for (Society society : parlamentarian.getSocieties().keySet()) {
				Hibernate.initialize(society);
				Hibernate.initialize(society.getCategories());
			}

			Hibernate.initialize(parlamentarian.getParty());
			Hibernate.initialize(parlamentarian.getParlamentarianType());
			Hibernate.initialize(parlamentarian.getDistrict());
			Hibernate.initialize(parlamentarian.getDistrict().getDistrictType());
			Hibernate.initialize(parlamentarian.getPermanentCommissions());
			Hibernate.initialize(parlamentarian.getSpecialCommissions());
			Hibernate.initialize(parlamentarian.getSocieties());
			Hibernate.initialize(parlamentarian.getStocks());

			for (Bill bill : parlamentarian.getAuthoredBills()) {
				Hibernate.initialize(bill.getCategories());
			}

			for (Bill bill : parlamentarian.getVotedBills()) {
				Hibernate.initialize(bill.getCategories());
			}

			for (Society society : parlamentarian.getSocieties().keySet()) {
				Hibernate.initialize(society.getCategories());
			}

			for (Stock stock : parlamentarian.getStocks().keySet()) {
				Hibernate.initialize(stock.getCategories());
			}

			hibernate.getTransaction().commit();
			return parlamentarian;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Parlamentarian saveParlamentarian(Parlamentarian parlamentarian) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(parlamentarian);
			hibernate.getTransaction().commit();
			return parlamentarian;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteParlamentarian(Parlamentarian parlamentarian) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(parlamentarian);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public List<Parlamentarian> searchParlamentarian(Category category) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			List<Parlamentarian> parlamentarians = new ArrayList<Parlamentarian>();

			Criteria parlamentarianCriteria = hibernate.createCriteria(Parlamentarian.class);
			parlamentarianCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			parlamentarianCriteria.setFetchMode("party", FetchMode.JOIN);
			parlamentarianCriteria.createAlias("relatedCategories", "cat").add(Restrictions.eq("cat.id", category.getId()));
			parlamentarians = (List<Parlamentarian>)parlamentarianCriteria.list();

			Collections.sort(parlamentarians, new Comparator<Parlamentarian>() {

				@Override
				public int compare(Parlamentarian o1, Parlamentarian o2) {
					return o1.compareTo(o2);
				}
			});
			hibernate.getTransaction().commit();
			return parlamentarians;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public List<Parlamentarian> getParlamentariansByBill(Bill bill) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();

			bill = (Bill) hibernate.get(Bill.class, bill.getId());
			List<Parlamentarian> parlamentarians = new ArrayList<Parlamentarian>(bill.getRelatedParlamentarians());

			Collections.sort(parlamentarians, new Comparator<Parlamentarian>() {

				@Override
				public int compare(Parlamentarian o1, Parlamentarian o2) {
					return o1.compareTo(o2);
				}
			});

			hibernate.getTransaction().commit();
			return parlamentarians;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public List<Parlamentarian> getBillAuthors(Bill bill) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();

			Criteria criteria = hibernate.createCriteria(Parlamentarian.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("authoredBills", FetchMode.JOIN);
			criteria.createCriteria("authoredBills", "ab");
			criteria.add(Restrictions.eq("ab.id", bill.getId()));

			List<Parlamentarian> parlamentarians = criteria.list();
			hibernate.getTransaction().commit();
			return parlamentarians;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
