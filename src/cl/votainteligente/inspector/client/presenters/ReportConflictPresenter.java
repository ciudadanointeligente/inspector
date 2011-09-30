package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.ParlamentarianServiceAsync;
import cl.votainteligente.inspector.client.services.RecaptchaRemoteServiceAsync;
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
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.claudiushauptmann.gwt.recaptcha.client.RecaptchaWidget;

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
		void setRecaptcha(String recaptchaPublicKey);
		RecaptchaWidget getRecaptcha();
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<ReportConflictPresenter> {
	}

	@Inject
	private ApplicationMessages applicationMessages;
	@Inject
	private PlaceManager placeManager;
	@Inject
	private RecaptchaRemoteServiceAsync recaptchaService;
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
		createReCaptcha();
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
		if (getView().getSelectedParlamentarianId() == 0) {
			fireEvent(new HideLoadingEvent());
			Window.alert(applicationMessages.getErrorUnselectedParliamentarian());
			return;
		}
		if (getView().getReport() == null || getView().getReport().length() == 0 || getView().getReport().equals("")) {
			fireEvent(new HideLoadingEvent());
			Window.alert(applicationMessages.getErrorEmptyReportField());
			return;
		}

		RecaptchaWidget rw = getView().getRecaptcha();
		recaptchaService.verifyChallenge(rw.getChallenge(), rw.getResponse(), new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				Window.alert(applicationMessages.getErrorRecaptchaValidationSystem());
			}

			public void onSuccess(Boolean result) {
				if (!result) {
					fireEvent(new HideLoadingEvent());
					Window.alert(applicationMessages.getErrorRecaptchaValidationCodeIsIncorrect());
				} else {
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
									PlaceRequest placeRequest = new PlaceRequest(ParlamentarianPresenter.PLACE)
									.with(ParlamentarianPresenter.PARAM_PARLAMENTARIAN_ID, parlamentarianId.toString());
									placeManager.revealPlace(placeRequest.with(ParlamentarianPresenter.PARAM_PARLAMENTARIAN_ID, parlamentarianId.toString()));
								}
							});
							fireEvent(new HideLoadingEvent());
						}
					});
				}
				fireEvent(new HideLoadingEvent());
			}
		});
	}

	public void createReCaptcha() {
		recaptchaService.getPublicKey(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorRecaptchaValidationSystem());
			}

			@Override
			public void onSuccess(String result) {
				getView().setRecaptcha(result);
			}
		});
	}
}
