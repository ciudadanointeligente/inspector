package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.ParlamentarianServiceAsync;
import cl.votainteligente.inspector.client.services.ReportConflictServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.ReportConflictUiHandlers;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.ReportConflict;
import cl.votainteligente.inspector.shared.HideLoadingEvent;
import cl.votainteligente.inspector.shared.ShowLoadingEvent;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import javax.inject.Inject;

public class ReportConflictPresenter extends Presenter<ReportConflictPresenter.MyView, ReportConflictPresenter.MyProxy> implements ReportConflictUiHandlers {
	public static final String PLACE = "reportConflict";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";

	public interface MyView extends View, HasUiHandlers<ReportConflictUiHandlers> {
		void setNotification(String message);
		void setSelectedParlamentarian(Integer selectedParlamentarianIndex);
		void setupParlamentarianList();
		void addParlamentarian(Parlamentarian parlamentarian);
		void clearForm();
		Long getSelectedParlamentarianId();
		String getReport();
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<ReportConflictPresenter> {
	}

	@Inject
	private ApplicationMessages applicationMessages;
	@Inject
	private ReportConflictServiceAsync reportConflictService;
	@Inject
	private ParlamentarianServiceAsync parlamentarianService;
	private Long parlamentarianId;

	@Inject
	public ReportConflictPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
	}

	@Override
	protected void onReveal() {
		getView().clearForm();
		getParlamentarianList();
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		try {
			parlamentarianId = Long.parseLong(placeRequest.getParameter(PARAM_PARLAMENTARIAN_ID, null));
		} catch (NumberFormatException nfe) {
			parlamentarianId = null;
		}
	}

	public void getParlamentarianList() {
		fireEvent(new ShowLoadingEvent());
		parlamentarianService.getAllParlamentarians(new AsyncCallback<List<Parlamentarian>>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				Window.alert(applicationMessages.getErrorParlamentarianList());
			}

			@Override
			public void onSuccess(List<Parlamentarian> result) {
				if (result != null) {
					getView().setupParlamentarianList();
					Integer selectedParlamentarianIndex = 0;
					Integer i = 1;
					for (Parlamentarian parlamentarian : result) {
						getView().addParlamentarian(parlamentarian);
						if (parlamentarianId != null && parlamentarian.getId().equals(parlamentarianId)) {
							selectedParlamentarianIndex = i;
						}
						i++;
					}
					if (parlamentarianId != null) {
						getView().setSelectedParlamentarian(selectedParlamentarianIndex);
					}
				}
				fireEvent(new HideLoadingEvent());
			}
		});
	}

	@Override
	public void submit() {
		fireEvent(new ShowLoadingEvent());
		parlamentarianId = getView().getSelectedParlamentarianId();
		parlamentarianService.getParlamentarian(parlamentarianId, new AsyncCallback<Parlamentarian>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				Window.alert(applicationMessages.getErrorParlamentarian());
			}

			@Override
			public void onSuccess(Parlamentarian result) {
				fireEvent(new ShowLoadingEvent());
				ReportConflict reportConflict = new ReportConflict();
				reportConflict.setReport(getView().getReport());
				reportConflictService.saveReportConflict(reportConflict, parlamentarianId, new AsyncCallback<ReportConflict>() {

					@Override
					public void onFailure(Throwable caught) {
						fireEvent(new HideLoadingEvent());
						Window.alert(applicationMessages.getErrorReportConflictSave());
					}

					@Override
					public void onSuccess(ReportConflict result) {
						fireEvent(new HideLoadingEvent());
						Window.alert(applicationMessages.getReportConflictSuccess());
					}
				});
				fireEvent(new HideLoadingEvent());
			}
		});
	}
}
