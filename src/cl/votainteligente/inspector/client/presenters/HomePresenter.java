package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

public class HomePresenter extends CustomWidgetPresenter<HomePresenter.Display> implements HomePresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(HomePresenterIface presenter);
		void setParlamentaryData(ListDataProvider<Parlamentarian> data);
		void setCategoryData(ListDataProvider<Category> data);
		void setBillData(ListDataProvider<Bill> data);
		void getParlamentarianSearch();
		void getCategorySearch();
	}

	@Inject
	public HomePresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	public void setup() {
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}
}
