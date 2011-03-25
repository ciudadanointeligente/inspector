package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Category;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwtrpc/category")
public interface CategoryService extends RemoteService {
	List<Category> getAllCategories() throws Exception;
	Category getCategory(Long categoryId) throws Exception;
	Category saveCategory(Category category) throws Exception;
	void deleteCategory(Category category) throws Exception;
}