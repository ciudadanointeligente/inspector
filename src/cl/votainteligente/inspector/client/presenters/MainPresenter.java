package cl.votainteligente.inspector.client.presenters;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

public class MainPresenter extends CustomWidgetPresenter<MainPresenter.Display> implements MainPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(MainPresenterIface presenter);
		FlowPanel getLayout();
	}

	@Inject
	public MainPresenter(Display display, EventBus eventBus) {
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
