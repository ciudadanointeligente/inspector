package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.ParlamentarianService;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.Society;

import org.hibernate.*;

import java.util.List;

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
}
