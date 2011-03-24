package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.PartyService;
import cl.votainteligente.inspector.model.Party;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PartyServiceImpl implements PartyService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Party> getAllParties() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Party.class);
			List<Party> parties = criteria.list();
			hibernate.getTransaction().commit();
			return parties;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Party getParty(Long partyId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Party party = (Party) hibernate.get(Party.class, partyId);
			hibernate.getTransaction().commit();
			return party;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Party saveParty(Party party) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(party);
			hibernate.getTransaction().commit();
			return party;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteParty(Party party) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(party);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
