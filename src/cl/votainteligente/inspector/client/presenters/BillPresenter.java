package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.BillServiceAsync;
import cl.votainteligente.inspector.model.Bill;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.Date;

public class BillPresenter extends Presenter<BillPresenter.MyView, BillPresenter.MyProxy> implements BillPresenterIface {
	public static final String PLACE = "bill";
	public static final String PARAM_BILL_ID = "billId";

	public interface MyView extends View {
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

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<BillPresenter> {
	}

	@Inject
	private BillServiceAsync billService;
	@Inject
	private ApplicationMessages applicationMessages;
	private Long billId;

	@Inject
	public BillPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void onBind() {
		getView().setPresenter(this);
	}

	@Override
	protected void onReset() {
		if (billId != null) {
			showBill();
		}
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.TYPE_MAIN_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		try {
			billId = Long.parseLong(placeRequest.getParameter(PARAM_BILL_ID, null));
		} catch (NumberFormatException nfe) {
			billId = null;
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
		billService.getBill(billId, new AsyncCallback<Bill>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorBill());
			}

			@Override
			public void onSuccess(Bill result) {
				getView().setBillBulletinNumber(result.getBulletinNumber());
				getView().setBillTitle(result.getTitle());
				getView().setBillEntryDate(result.getEntryDate());
				getView().setBillInitiativeType(result.getInitiativeType().getName());
				getView().setBillType(result.getBillType().getName());
				getView().setBillOriginChamber(result.getOriginChamber().getName());
				getView().setBillUrgency(result.getUrgency().getName());
				getView().setBillStage(result.getStage().getName());
			}
		});
	}
}
