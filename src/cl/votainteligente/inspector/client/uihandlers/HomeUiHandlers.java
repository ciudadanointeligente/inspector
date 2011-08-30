package cl.votainteligente.inspector.client.uihandlers;

import cl.votainteligente.inspector.client.presenters.HomePresenter.SelectionType;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.shared.NotificationEventType;

import com.gwtplatform.mvp.client.UiHandlers;

public interface HomeUiHandlers extends UiHandlers {
	void searchParlamentarian(String keyWord);
	void searchParlamentarian(Category category);
	void searchCategory(String keyWord);
	void searchCategory(Parlamentarian parlamentarian);
	void searchBill(Long parlamentarianId, Long categoryId);
	void showParlamentarianProfile();
	void switchSelectionType();
	void setupSelection(SelectionType changeType);
	void searchCleaner();
	void showNotification(String message, NotificationEventType type);
}
