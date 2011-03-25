package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.inject.ServiceInjector;
import cl.votainteligente.inspector.model.Bill;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.Date;

public class BillPresenter extends WidgetPresenter<BillPresenter.Display> implements BillPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(BillPresenterIface presenter);
		String getBillBulletinNumber();
		void setBillBulletinNumber(String billBulletinNumber);
		String getBillTitle();
		void setBillTitle(String billTitle);
		String getBillEntryDate();
		void setBillEntryDate(Date billEntryDate);
		String getBillInitiativeType();
		void setBillInitiativeType(String billInitiativeType);
		String getBillType();
		void setBillType(String billType);
		String getBillOriginChamber();
		void setBillOriginChamber(String billOriginChamber);
		String getBillUrgency();
		void setBillUrgency(String billUrgency);
		String getBillStage();
		void setBillStage(String billStage);
	}

	private Long billId;
	private static final ServiceInjector serviceInjector = GWT.create(ServiceInjector.class);

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
		serviceInjector.getBillService().getBill(billId, new AsyncCallback<Bill>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("No se ha podido cargar el proyecto de ley");
			}

			@Override
			public void onSuccess(Bill result) {
				display.setBillBulletinNumber(result.getBulletinNumber());
				display.setBillTitle(result.getTitle());
				display.setBillEntryDate(result.getEntryDate());
				display.setBillInitiativeType(result.getInitiativeType().getName());
				display.setBillType(result.getBillType().getName());
				display.setBillOriginChamber(result.getOriginChamber().getName());
				display.setBillUrgency(result.getUrgency().getName());
				display.setBillStage(result.getStage().getName());
			}
		});
	}
}
