package cl.votainteligente.inspector.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetContainerDisplay;
import net.customware.gwt.presenter.client.widget.WidgetContainerPresenter;

import com.google.inject.Inject;

public class PopupPresenter extends WidgetContainerPresenter<PopupPresenter.Display> {
	public interface Display extends WidgetContainerDisplay {
	}

	@Inject
	public PopupPresenter(Display display, EventBus eventBus, SocietyPresenter societyPresenter) {
		super(display, eventBus, societyPresenter);
		bind();
	}
}
