package cl.votainteligente.inspector.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.inject.Inject;

public class BillPresenter extends WidgetPresenter<BillPresenter.Display> implements BillPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(BillPresenterIface presenter);
	}

	private Long billId;

	@Inject
	public BillPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
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
		if (billId != null) {
			showBill();
		}
	}

	@Override
	public Long getBillId() {
		return billId;
	}

	@Override
	public void setBillId(Long billId) {
		this.billId = billId;
	}

	@Override
	public void showBill() {
		// TODO: fill this method
	}

}
