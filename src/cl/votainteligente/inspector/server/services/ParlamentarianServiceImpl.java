package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.ParlamentarianService;
import cl.votainteligente.inspector.model.*;

import org.hibernate.*;
import org.hibernate.criterion.*;

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
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("party", FetchMode.JOIN);
			criteria.setFetchMode("authoredBills", FetchMode.JOIN);
			criteria.setFetchMode("votedBills", FetchMode.JOIN);
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

			for (Bill bill : parlamentarian.getAuthoredBills()) {
				Hibernate.initialize(bill.getCategories());
			}

			for (Bill bill : parlamentarian.getVotedBills()) {
				Hibernate.initialize(bill.getCategories());
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
	public List<Parlamentarian> searchParlamentarian(String keyWord) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Parlamentarian.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("party", FetchMode.JOIN);
			criteria.setFetchMode("authoredBills", FetchMode.JOIN);
			criteria.setFetchMode("votedBills", FetchMode.JOIN);

			if (keyWord != null && !keyWord.equals("")) {
				Conjunction keywordConjunction = Restrictions.conjunction();
				Disjunction keyWordDisjunction;
				String[] keyWords = keyWord.split("[ ]");

				for (int i = 0; i < keyWords.length; i++) {
					keyWords[i]  = keyWords[i].replaceAll("\\W", "");
					keyWordDisjunction = Restrictions.disjunction();
					keyWordDisjunction.add(Restrictions.ilike("firstName", keyWords[i], MatchMode.ANYWHERE));
					keyWordDisjunction.add(Restrictions.ilike("lastName", keyWords[i], MatchMode.ANYWHERE));
					keywordConjunction.add(keyWordDisjunction);
				}
				criteria.add(keywordConjunction);
			}

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
	public List<Parlamentarian> searchParlamentarian(List<Category> categories) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			List<Parlamentarian> parlamentarians = new ArrayList<Parlamentarian>();

			if (categories.size() > 0) {

				Criteria parlamentarianCriteria = hibernate.createCriteria(Parlamentarian.class);
				parlamentarianCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				parlamentarianCriteria.setFetchMode("party", FetchMode.JOIN);
				parlamentarians = (List<Parlamentarian>)parlamentarianCriteria.list();

				Set<Parlamentarian> billRelatedParlamentarians = new HashSet<Parlamentarian>();
				Set<Parlamentarian> societyRelatedParlamentarians = new HashSet<Parlamentarian>();
				Set<Parlamentarian> resultSet = new HashSet<Parlamentarian>();

				forParlamentarian:
				for (Parlamentarian parlamentarian : parlamentarians) {
					for (Category category : categories) {
						for (Society society : parlamentarian.getSocieties().keySet()) {
							if (society.getCategories().contains(category)) {
								societyRelatedParlamentarians.add(parlamentarian);
							}
						}
					}

					for (Category category : categories) {
						for (Bill bill : parlamentarian.getAuthoredBills()) {
							if (bill.getCategories().contains(category)) {
								billRelatedParlamentarians.add(parlamentarian);
								continue forParlamentarian;
							}
						}
					}

					for (Category category : categories) {
						for (Bill bill : parlamentarian.getVotedBills()) {
							if (bill.getCategories().contains(category)) {
								billRelatedParlamentarians.add(parlamentarian);
								continue forParlamentarian;
							}
						}
					}
				}

				for (Parlamentarian parlamentarian : billRelatedParlamentarians) {
					if (societyRelatedParlamentarians.contains(parlamentarian)) {
						resultSet.add(parlamentarian);
					}
				}

				parlamentarians = new ArrayList<Parlamentarian>(resultSet);
				Collections.sort(parlamentarians, new Comparator<Parlamentarian>() {

					@Override
					public int compare(Parlamentarian o1, Parlamentarian o2) {
						return o1.compareTo(o2);
					}
				});
			}
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

			Criteria criteria = hibernate.createCriteria(Parlamentarian.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("party", FetchMode.JOIN);
			criteria.setFetchMode("authoredBills", FetchMode.JOIN);
			criteria.setFetchMode("votedBills", FetchMode.JOIN);

			// Adds subcriterias used to search in collections
			criteria.createCriteria("authoredBills").add(Restrictions.eq("id", bill.getId()));
			criteria.createCriteria("votedBills").add(Restrictions.eq("id", bill.getId()));

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
