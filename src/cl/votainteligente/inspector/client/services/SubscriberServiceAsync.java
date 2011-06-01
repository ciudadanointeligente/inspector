package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Subscriber;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface SubscriberServiceAsync {
	void getAllSubscribers(AsyncCallback<List<Subscriber>> callback);
	void getSubscriber(Long subscriberId, AsyncCallback<Subscriber> callback);
	void getSubscriberByEmail(String subscriberEmail, AsyncCallback<Subscriber> callback);
	void saveSubscriber(Subscriber subscriber, AsyncCallback<Subscriber> callback);
	void deleteSubscriber(Subscriber subscriber, AsyncCallback<Void> callback);
	void addBillSubscription(Long subscriberId, Long billId, AsyncCallback<Subscriber> callback);
	void addCategorySubscription(Long subscriberId, Long categoryId, AsyncCallback<Subscriber> callback);
	void deleteBillSubscription(Long subscriberId, String subscriberKey, Long billId, AsyncCallback<Boolean> callback);
	void deleteCategorySubscription(Long subscriberId, String subscriberKey, Long categoryId, AsyncCallback<Boolean> callback);
	void deleteAllSubscriptions(Long subscriberId, String subscriberKey, AsyncCallback<Boolean> callback);
}
