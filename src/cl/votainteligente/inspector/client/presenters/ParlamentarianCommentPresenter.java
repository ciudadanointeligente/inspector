package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.ParlamentarianCommentServiceAsync;
import cl.votainteligente.inspector.client.services.ParlamentarianServiceAsync;
import cl.votainteligente.inspector.client.services.RecaptchaRemoteServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.ParlamentarianCommentUiHandlers;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.ParlamentarianComment;
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
import com.google.inject.Inject;

import com.claudiushauptmann.gwt.recaptcha.client.RecaptchaWidget;

import java.util.List;

public class ParlamentarianCommentPresenter extends Presenter<ParlamentarianCommentPresenter.MyView, ParlamentarianCommentPresenter.MyProxy> implements ParlamentarianCommentUiHandlers {
	public static final String PLACE = "parlamentarianComment";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";

	public interface MyView extends View, HasUiHandlers<ParlamentarianCommentUiHandlers> {
		Long getSelectedParlamentarianId();
		void setSelectedParlamentarian(Integer selectedParlamentarianIndex);
		void setupParlamentarianList();
		void addParlamentarian(Parlamentarian parlamentarian);
		String getCommentSubject();
		String getCommentBody();
		void clearForm();
		void setRecaptcha(String recaptchaPublicKey);
		RecaptchaWidget getRecaptcha();
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<ParlamentarianCommentPresenter> {
	}

	@Inject
	private ApplicationMessages applicationMessages;
	@Inject
	private PlaceManager placeManager;
	@Inject
	private RecaptchaRemoteServiceAsync recaptchaService;
	@Inject
	private ParlamentarianServiceAsync parlamentarianService;
	@Inject
	private ParlamentarianCommentServiceAsync parlamentarianCommentService;
	private Long parlamentarianId;

	@Inject
	public ParlamentarianCommentPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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
	public void saveParlamentarianComment() {
		fireEvent(new ShowLoadingEvent());
		if (getView().getSelectedParlamentarianId() == 0) {
			fireEvent(new HideLoadingEvent());
			Window.alert(applicationMessages.getErrorUnselectedParliamentarian());
		} else {
			if (getView().getCommentBody() == null || getView().getCommentBody().length() == 0 || getView().getCommentBody().equals("")) {
				fireEvent(new HideLoadingEvent());
				Window.alert(applicationMessages.getErrorEmptyCommentField());
			} else if (getView().getCommentSubject() == null || getView().getCommentSubject().length() == 0 || getView().getCommentSubject().equals("")) {
				fireEvent(new HideLoadingEvent());
				Window.alert(applicationMessages.getErrorEmptySubject());
			} else {
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
							parlamentarianService.getParlamentarian(getView().getSelectedParlamentarianId(), new AsyncCallback<Parlamentarian>() {

								@Override
								public void onFailure(Throwable caught) {
									fireEvent(new HideLoadingEvent());
									Window.alert(applicationMessages.getErrorParlamentarian());
								}

								@Override
								public void onSuccess(Parlamentarian result) {
									fireEvent(new ShowLoadingEvent());
									ParlamentarianComment parlamentarianComment = new ParlamentarianComment();
									parlamentarianComment.setSubject(getView().getCommentSubject());
									parlamentarianComment.setBody(getView().getCommentBody());
									parlamentarianCommentService.saveParlamentarianComment(parlamentarianComment, parlamentarianId, new AsyncCallback<ParlamentarianComment>() {

										@Override
										public void onFailure(Throwable caught) {
											fireEvent(new HideLoadingEvent());
											Window.alert(applicationMessages.getErrorParlamentarianCommentSave());
										}

										@Override
										public void onSuccess(ParlamentarianComment result) {
											fireEvent(new HideLoadingEvent());
											Window.alert(applicationMessages.getParlamentarianCommentSaved());
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
		}
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
