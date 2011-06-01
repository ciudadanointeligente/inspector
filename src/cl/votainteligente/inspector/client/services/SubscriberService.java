package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Subscriber;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/subscriber")
public interface SubscriberService extends RemoteService {
	List<Subscriber> getAllSubscribers() throws Exception;
	Subscriber getSubscriber(Long subscriberId) throws Exception;
	Subscriber getSubscriberByEmail(String subscriberEmail) throws Exception;
	Subscriber saveSubscriber(Subscriber subscriber) throws Exception;
	void deleteSubscriber(Subscriber subscriber) throws Exception;
	Subscriber addBillSubscription(Long subscriberId, Long billId) throws Exception;
	Subscriber addCategorySubscription(Long subscriberId, Long categoryId) throws Exception;
	Boolean deleteBillSubscription(Long subscriberId, String subscriberKey, Long billId) throws Exception;
	Boolean deleteCategorySubscription(Long subscriberId, String subscriberKey, Long categoryId) throws Exception;
	Boolean deleteAllSubscriptions(Long subscriberId, String subscriberKey) throws Exception;
}
