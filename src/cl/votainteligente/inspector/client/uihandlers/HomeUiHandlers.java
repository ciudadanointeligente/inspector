package cl.votainteligente.inspector.client.uihandlers;

import cl.votainteligente.inspector.client.presenters.HomePresenter.SelectionType;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import com.gwtplatform.mvp.client.UiHandlers;

import java.util.List;

public interface HomeUiHandlers extends UiHandlers {
	void searchParlamentarian(String keyWord);
	void searchParlamentarian(List<Category> categories);
	void searchCategory(String keyWord);
	void searchCategory(List<Parlamentarian> parlamentarians);
	void searchBill(Long parlamentarianId, Long categoryId);
	void showParlamentarianProfile();
	void resetSelection();
	void switchSelectionType();
	void setupSelection(SelectionType changeType);
	void searchCleaner();
}
