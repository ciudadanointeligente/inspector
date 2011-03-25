package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Person;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/person")
public interface PersonService extends RemoteService {
	List<Person> getAllPersons() throws Exception;
	Person getPerson(Long personId) throws Exception;
	Person savePerson(Person person) throws Exception;
	void deletePerson(Person person) throws Exception;
}