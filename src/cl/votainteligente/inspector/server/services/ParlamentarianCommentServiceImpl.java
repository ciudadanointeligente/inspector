package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.ParlamentarianCommentService;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.ParlamentarianComment;
import cl.votainteligente.inspector.server.ApplicationProperties;
import cl.votainteligente.inspector.server.Emailer;
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
	public List<ParlamentarianComment> getAllParlamentarianComments(Long parlamentarianId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.load(Parlamentarian.class, parlamentarianId);
			Criteria criteria = hibernate.createCriteria(ParlamentarianComment.class);
			criteria.add(Restrictions.eq("parlamentarian", parlamentarian));
			criteria.add(Restrictions.eq("approved", true));
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
			parlamentarianComment.setApproved(false);
			parlamentarianComment.setRejected(false);
			parlamentarianComment.setKey(RandomPassword.getRandomString(100));
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.load(Parlamentarian.class, parlamentarianId);
			parlamentarianComment.setParlamentarian(parlamentarian);
			parlamentarianComment.setCreationDate(new Date());
			hibernate.saveOrUpdate(parlamentarianComment);

			String parlamentarianCommentLink = ApplicationProperties.getProperty("server.address") + "#parlamentarianComment";
			parlamentarianCommentLink += ";parlamentarianId=" + parlamentarian.getId().toString();
			parlamentarianCommentLink += ";parlamentarianCommentId=" + parlamentarianComment.getId().toString();
			parlamentarianCommentLink += ";parlamentarianCommentKey=" + parlamentarianComment.getKey();
			String parlamentarianName = parlamentarian.getFirstName() + " " + parlamentarian.getLastName();
			String messageBody = String.format(ApplicationProperties.getProperty("email.parlamentarian.body"), parlamentarianName, parlamentarianCommentLink);
			messageBody += ApplicationProperties.getProperty("email.signature");

			hibernate.getTransaction().commit();

			Emailer emailer = new Emailer();
			emailer.setSubject(ApplicationProperties.getProperty("email.parlamentarian.subject"));
			emailer.setBody(messageBody);
			emailer.setRecipient(parlamentarian.getEmail());
			emailer.connectAndSend();

			return parlamentarianComment;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public ParlamentarianComment approveParlamentarianComment(String key, Long id, Long parlamentarianId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.load(Parlamentarian.class, parlamentarianId);
			Criteria criteria = hibernate.createCriteria(ParlamentarianComment.class);
			criteria.add(Restrictions.eq("key", key));
			criteria.add(Restrictions.eq("id", id));
			criteria.add(Restrictions.eq("parlamentarian", parlamentarian));
			criteria.add(Restrictions.eq("rejected", false));
			ParlamentarianComment parlamentarianComment = (ParlamentarianComment) criteria.uniqueResult();
			parlamentarianComment.setApproved(true);
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
	public ParlamentarianComment rejectParlamentarianComment(String key, Long id, Long parlamentarianId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.load(Parlamentarian.class, parlamentarianId);
			Criteria criteria = hibernate.createCriteria(ParlamentarianComment.class);
			criteria.add(Restrictions.eq("key", key));
			criteria.add(Restrictions.eq("id", id));
			criteria.add(Restrictions.eq("parlamentarian", parlamentarian));
			criteria.add(Restrictions.eq("approved", false));
			ParlamentarianComment parlamentarianComment = (ParlamentarianComment) criteria.uniqueResult();
			parlamentarianComment.setRejected(true);
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
