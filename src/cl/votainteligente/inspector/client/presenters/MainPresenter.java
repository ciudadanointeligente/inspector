package cl.votainteligente.inspector.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetContainerDisplay;
import net.customware.gwt.presenter.client.widget.WidgetContainerPresenter;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

public class MainPresenter extends WidgetContainerPresenter<MainPresenter.Display> {
	public interface Display extends WidgetContainerDisplay {
		FlowPanel getLayout();
	}

	@Inject
	public MainPresenter(Display display, EventBus eventBus, HomePresenter homePresenter, BillPresenter billPresenter, ParlamentarianPresenter parlamentarianPresenter) {
		super(display, eventBus, homePresenter, billPresenter, parlamentarianPresenter);
		bind();
	}
}
