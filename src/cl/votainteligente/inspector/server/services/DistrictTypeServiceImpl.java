package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.DistrictTypeService;
import cl.votainteligente.inspector.model.DistrictType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DistrictTypeServiceImpl implements DistrictTypeService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<DistrictType> getAllDistrictTypes() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(DistrictType.class);
			List<DistrictType> districtTypes = criteria.list();
			hibernate.getTransaction().commit();
			return districtTypes;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public DistrictType getDistrictType(Long districtTypeId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			DistrictType districtType = (DistrictType) hibernate.get(DistrictType.class, districtTypeId);
			hibernate.getTransaction().commit();
			return districtType;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public DistrictType saveDistrictType(DistrictType districtType) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(districtType);
			hibernate.getTransaction().commit();
			return districtType;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteDistrictType(DistrictType districtType) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(districtType);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
