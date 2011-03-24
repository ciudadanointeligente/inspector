package cl.votainteligente.inspector.client.presenters;

import com.google.inject.Inject;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

public class BillPresenter extends CustomWidgetPresenter<BillPresenter.Display> implements BillPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(BillPresenterIface presenter);
	}

	@Inject
	public BillPresenter(Display display, EventBus eventBus) {
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
