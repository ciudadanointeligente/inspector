package cl.votainteligente.inspector.server;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Subscriber;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class EmailNotifier {
	private static final Logger logger = Logger.getLogger(EmailNotifier.class);
	private Properties props;
	private List<Bill> updatedBills;
	private List<Subscriber> subscribers;
	private SessionFactory sessionFactory;

	public EmailNotifier() {
		props = System.getProperties();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void sendNotifications() {
		Session hibernate = null;
		Set<Bill> intersection = new HashSet<Bill>();
		Set<Bill> updatedUnion = new HashSet<Bill>();
		Map<Long, String> billMessages = new HashMap<Long, String>();
		Set<Subscriber> subscribersToNofity = new HashSet<Subscriber>();

		try {
			hibernate = sessionFactory.getCurrentSession();
			hibernate.beginTransaction();

			Criteria criteria = hibernate.createCriteria(Bill.class);
			criteria.add(Restrictions.sqlRestriction("last_update > last_notification"));
			updatedBills = (List<Bill>)criteria.list();

			criteria = hibernate.createCriteria(Subscriber.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("subscriptions", FetchMode.JOIN);
			subscribers = (List<Subscriber>) criteria.list();
			hibernate.getTransaction().commit();

			for (Subscriber subscriber : subscribers) {
				intersection = new HashSet<Bill>(subscriber.getSubscriptions().keySet());
				intersection.retainAll(updatedBills);
				if (intersection.size() > 0) {
					subscribersToNofity.add(subscriber);
					updatedUnion.addAll(intersection);
				}
			}

			updatedBills = new ArrayList<Bill>(updatedUnion);

			String billLink = null;
			for (Bill bill : updatedBills) {
				billLink = "<a href=\"%s#bill;billId=%s\">%s</a><a href=\"%s#unsubscribe;billId=%s";
				billLink = String.format(billLink, ApplicationProperties.getProperty("server.address"),  bill.getId().toString(), ApplicationProperties.getProperty("server.address"),  bill.getId().toString());
				billLink += ";subscriberKey=%s;subscriberId=%s\">Desuscribir</a><br/>";
				billMessages.put(bill.getId(), billLink);
			}

			String messageBody = null;
			Emailer emailer = new Emailer();
			emailer.setSubject(ApplicationProperties.getProperty("email.newsletter.subject"));

			for (Subscriber subscriber : subscribersToNofity) {
				messageBody = "";
				for (Bill bill : subscriber.getSubscriptions().keySet()) {
					messageBody += String.format(billMessages.get(bill.getId()), subscriber.getSuscriberKey(), subscriber.getId().toString());
				}

				messageBody += ApplicationProperties.getProperty("email.signature");
				emailer.setRecipient(subscriber.getEmail());
				emailer.setBody(messageBody);
				try {
					emailer.connectAndSend();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error(e.getMessage(), e);
		}
	}

}
