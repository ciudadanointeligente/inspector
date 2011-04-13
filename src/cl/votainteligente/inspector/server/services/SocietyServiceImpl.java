package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.SocietyService;
import cl.votainteligente.inspector.model.Society;

import org.hibernate.*;

import java.util.List;

public class SocietyServiceImpl implements SocietyService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Society> getAllSocieties() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Society.class);
			List<Society> societies = criteria.list();
			hibernate.getTransaction().commit();
			return societies;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Society getSociety(Long societyId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Society society = (Society) hibernate.get(Society.class, societyId);
			Hibernate.initialize(society.getSocietyType());
			Hibernate.initialize(society.getSocietyStatus());
			Hibernate.initialize(society.getNotary());
			Hibernate.initialize(society.getMembers());
			Hibernate.initialize(society.getCategories());
			hibernate.getTransaction().commit();
			return society;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Society saveSociety(Society society) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(society);
			hibernate.getTransaction().commit();
			return society;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteSociety(Society society) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(society);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
