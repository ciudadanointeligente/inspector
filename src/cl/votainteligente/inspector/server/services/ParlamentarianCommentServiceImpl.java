package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.ParlamentarianCommentService;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.ParlamentarianComment;
import cl.votainteligente.inspector.server.RandomPassword;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class ParlamentarianCommentServiceImpl implements ParlamentarianCommentService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<ParlamentarianComment> getAllParlamentarianComments() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(ParlamentarianComment.class);
			criteria.addOrder(Order.asc("creationDate"));
			List<ParlamentarianComment> parlamentarianComments = criteria.list();
			hibernate.getTransaction().commit();
			return parlamentarianComments;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public ParlamentarianComment getParlamentarianComment(Long parlamentarianCommentId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			ParlamentarianComment parlamentarianComment = (ParlamentarianComment) hibernate.get(ParlamentarianComment.class, parlamentarianCommentId);
			hibernate.getTransaction().commit();
			return parlamentarianComment;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public ParlamentarianComment saveParlamentarianComment(ParlamentarianComment parlamentarianComment, Long parlamentarianId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			parlamentarianComment.setAproved(false);
			parlamentarianComment.setKey(RandomPassword.getRandomString(100));
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.load(Parlamentarian.class, parlamentarianId);
			parlamentarianComment.setParlamentarian(parlamentarian);
			parlamentarianComment.setCreationDate(new Date());
			hibernate.saveOrUpdate(parlamentarianComment);
			hibernate.getTransaction().commit();
			return parlamentarianComment;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public ParlamentarianComment aproveParlamentarianComment(String key) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(ParlamentarianComment.class);
			criteria.add(Restrictions.eq("key", key));
			ParlamentarianComment parlamentarianComment = (ParlamentarianComment) criteria.uniqueResult();
			parlamentarianComment.setAproved(true);
			hibernate.saveOrUpdate(parlamentarianComment);
			hibernate.getTransaction().commit();
			return parlamentarianComment;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteParlamentarianComment(ParlamentarianComment parlamentarianComment) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(parlamentarianComment);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
