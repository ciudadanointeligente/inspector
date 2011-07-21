package cl.votainteligente.inspector.client.services;

import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface CategoryServiceAsync {
	void getAllCategories(AsyncCallback<List<Category>> callback);
	void getCategory(Long categoryId, AsyncCallback<Category> callback);
	void saveCategory(Category category, AsyncCallback<Category> callback);
	void deleteCategory(Category category, AsyncCallback<Void> callback);
	void searchCategory(String keyWord, AsyncCallback<List<Category>> callback);
	void searchCategory(Parlamentarian parlamentarian, AsyncCallback<List<Category>> callback);
}
