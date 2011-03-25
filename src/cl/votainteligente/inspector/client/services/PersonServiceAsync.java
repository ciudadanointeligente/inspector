package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Person;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface PersonServiceAsync {
	void getAllPersons(AsyncCallback<List<Person>> callback);
	void getPerson(Long personId, AsyncCallback<Person> callback);
	void savePerson(Person person, AsyncCallback<Person> callback);
	void deletePerson(Person person, AsyncCallback<Void> callback);
}
