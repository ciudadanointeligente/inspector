package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.InitiativeTypeService;
import cl.votainteligente.inspector.model.InitiativeType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class InitiativeTypeServiceImpl implements InitiativeTypeService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<InitiativeType> getAllInitiativeTypes() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(InitiativeType.class);
			List<InitiativeType> initiativeTypes = criteria.list();
			hibernate.getTransaction().commit();
			return initiativeTypes;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public InitiativeType getInitiativeType(Long initiativeTypeId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			InitiativeType initiativeType = (InitiativeType) hibernate.get(InitiativeType.class, initiativeTypeId);
			hibernate.getTransaction().commit();
			return initiativeType;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public InitiativeType saveInitiativeType(InitiativeType initiativeType) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(initiativeType);
			hibernate.getTransaction().commit();
			return initiativeType;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteInitiativeType(InitiativeType initiativeType) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(initiativeType);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
