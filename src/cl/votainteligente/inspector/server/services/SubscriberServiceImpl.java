package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.SubscriberService;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Subscriber;
import cl.votainteligente.inspector.server.RandomPassword;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public class SubscriberServiceImpl implements SubscriberService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Subscriber> getAllSubscribers() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Subscriber.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("subscriptions", FetchMode.JOIN);
			List<Subscriber> subscribers = criteria.list();
			hibernate.getTransaction().commit();
			return subscribers;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Subscriber getSubscriber(Long subscriberId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Subscriber subscriber = (Subscriber) hibernate.get(Subscriber.class, subscriberId);
			Hibernate.initialize(subscriber.getSubscriptions());
			hibernate.getTransaction().commit();
			return subscriber;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Subscriber getSubscriberByEmail(String subscriberEmail) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		Subscriber subscriber = new Subscriber();
		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Subscriber.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("subscriptions", FetchMode.JOIN);
			criteria.add(Restrictions.eq("email", subscriberEmail));
			subscriber = (Subscriber) criteria.uniqueResult();
			hibernate.getTransaction().commit();
			return subscriber;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Subscriber saveSubscriber(Subscriber subscriber) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			if (subscriber.getId() == null) {
				subscriber.setSuscriberKey(RandomPassword.getRandomString(200));
			}
			hibernate.saveOrUpdate(subscriber);
			hibernate.getTransaction().commit();
			return subscriber;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteSubscriber(Subscriber subscriber) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			hibernate.delete(subscriber);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Subscriber addBillSubscription(Long subscriberId, Long billId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Bill bill = (Bill) hibernate.get(Bill.class, billId);
			Integer count = 1;
			Subscriber subscriber = (Subscriber) hibernate.get(Subscriber.class, subscriberId);
			Map<Bill, Integer> subscriptions = subscriber.getSubscriptions();
			if (!subscriptions.containsKey(bill)) {
				subscriptions.put(bill, count);
				subscriber.setSubscriptions(subscriptions);
				hibernate.saveOrUpdate(subscriber);
			}
			hibernate.getTransaction().commit();
			return subscriber;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Subscriber addCategorySubscription(Long subscriberId, Long categoryId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Subscriber subscriber = (Subscriber) hibernate.get(Subscriber.class, subscriberId);
			Criteria billCriteria = hibernate.createCriteria(Bill.class);
			billCriteria.createCriteria("categories").add(Restrictions.eq("id", categoryId));
			List<Bill> bills = billCriteria.list();
			Map<Bill, Integer> subscriptions = subscriber.getSubscriptions();
			Integer count;

			for (Bill bill : bills) {
				count = 1;
				if (subscriptions.containsKey(bill)) {
					count = subscriptions.get(bill) + 1;
					subscriptions.remove(bill);
					subscriptions.put(bill, count);
				} else {
					subscriptions.put(bill, count);
				}
			}

			subscriber.setSubscriptions(subscriptions);
			hibernate.saveOrUpdate(subscriber);
			hibernate.getTransaction().commit();
			return subscriber;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Boolean deleteBillSubscription(Long subscriberId, String subscriberKey, Long billId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Subscriber subscriber = (Subscriber) hibernate.get(Subscriber.class, subscriberId);

			if (!subscriber.getSuscriberKey().equals(subscriberKey)) {
				hibernate.getTransaction().commit();
				return false;
			}

			Bill bill = (Bill) hibernate.get(Bill.class, billId);
			Map<Bill, Integer> subscriptions = subscriber.getSubscriptions();
			subscriptions.remove(bill);
			subscriber.setSubscriptions(subscriptions);
			hibernate.saveOrUpdate(subscriber);
			hibernate.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Boolean deleteCategorySubscription(Long subscriberId, String subscriberKey, Long categoryId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Subscriber subscriber = (Subscriber) hibernate.get(Subscriber.class, subscriberId);

			if (!subscriber.getSuscriberKey().equals(subscriberKey)) {
				hibernate.getTransaction().commit();
				return false;
			}

			Criteria billCriteria = hibernate.createCriteria(Bill.class);
			billCriteria.createCriteria("categories").add(Restrictions.eq("id", categoryId));
			List<Bill> bills = billCriteria.list();
			Map<Bill, Integer> subscriptions = subscriber.getSubscriptions();
			Integer count;

			for (Bill bill : bills) {
				count = 1;
				if (subscriptions.containsKey(bill)) {
					count = subscriptions.get(bill) - 1;
					subscriptions.remove(bill);
					if (count > 0) {
						subscriptions.put(bill, count);
					}
				}
			}

			subscriber.setSubscriptions(subscriptions);
			hibernate.saveOrUpdate(subscriber);
			hibernate.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public Boolean deleteAllSubscriptions(Long subscriberId, String subscriberKey) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();

			Subscriber subscriber = (Subscriber) hibernate.get(Subscriber.class, subscriberId);

			if (!subscriber.getSuscriberKey().equals(subscriberKey)) {
				hibernate.getTransaction().commit();
				return false;
			}

			Map<Bill, Integer> subscriptions = subscriber.getSubscriptions();

			for (Bill bill : subscriptions.keySet()) {
				subscriptions.remove(bill);
			}
			subscriber.setSubscriptions(subscriptions);
			hibernate.saveOrUpdate(subscriber);
			hibernate.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
