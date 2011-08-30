package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.ParlamentarianCommentServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.ParlamentarianCommentApprovalUiHandlers;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ParlamentarianCommentApprovalPresenter extends Presenter<ParlamentarianCommentApprovalPresenter.MyView, ParlamentarianCommentApprovalPresenter.MyProxy> implements ParlamentarianCommentApprovalUiHandlers {
	public static final String PLACE = "parlamentarianCommentApproval";
	public static final String PARAM_PARLAMENTARIAN_COMMENT_KEY = "parlamentarianCommentKey";
	public static final String PARAM_PARLAMENTARIAN_COMMENT_ID = "parlamentarianCommentId";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";

	public interface MyView extends View, HasUiHandlers<ParlamentarianCommentApprovalUiHandlers> {
		void setCommentSubject(String commentSubject);
		void setCommentBody(String commentBody);
		void setCommentCreationDate(String commentCreationDate);
		void setMessage(String message);
		void setProfileLink(String href);
		void hideButtons();
		void clear();
	}

	@Inject
	ApplicationMessages applicationMessages;
	@Inject
	private PlaceManager placeManager;
	@Inject
	ParlamentarianCommentServiceAsync parlamentarianCommentService;
	private String parlamentarianCommentKey;
	private Long parlamentarianCommentId;
	private Long parlamentarianId;
	private ParlamentarianComment parlamentarianComment;

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<ParlamentarianCommentApprovalPresenter> {}

	@Inject
	public ParlamentarianCommentApprovalPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {
		getView().clear();
		if (parlamentarianCommentId != null) {
			getParlamentarianComment(parlamentarianCommentId);
			getView().setProfileLink("#" + placeManager.buildHistoryToken(new PlaceRequest(ParlamentarianPresenter.PLACE).with(PARAM_PARLAMENTARIAN_ID, parlamentarianId.toString())));
		} else {
			Window.alert(applicationMessages.getErrorParlamentarianCommentApproval());
			placeManager.revealPlace(new PlaceRequest(HomePresenter.PLACE));
		}
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
			parlamentarianCommentId = Long.parseLong(placeRequest.getParameter(PARAM_PARLAMENTARIAN_COMMENT_ID, null));
			parlamentarianCommentKey = placeRequest.getParameter(PARAM_PARLAMENTARIAN_COMMENT_KEY, null);
		} catch (NumberFormatException nfe) {
			parlamentarianId = null;
			parlamentarianCommentId = null;
			parlamentarianCommentKey = null;
		}
	}

	public void getParlamentarianComment(Long parlamentarianCommentId) {
		fireEvent(new ShowLoadingEvent());
		parlamentarianCommentService.getParlamentarianComment(parlamentarianCommentId, new AsyncCallback<ParlamentarianComment>() {

			@Override
			public void onFailure(Throwable caught) {
				fireEvent(new HideLoadingEvent());
				Window.alert(applicationMessages.getErrorParlamentarianComment());
			}

			@Override
			public void onSuccess(ParlamentarianComment result) {
				if (result != null) {
					parlamentarianComment = result;

					if (parlamentarianComment.getSubject() != null) {
						getView().setCommentSubject(parlamentarianComment.getSubject());
					}

					if (parlamentarianComment.getBody() != null) {
						getView().setCommentBody(parlamentarianComment.getBody());
					}

					if (parlamentarianComment.getCreationDate() != null) {
						getView().setCommentCreationDate(DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).format(parlamentarianComment.getCreationDate()));
					}

					if (parlamentarianComment.getApproved() != null && parlamentarianComment.getApproved()) {
						getView().hideButtons();
						getView().setMessage(applicationMessages.getParlamentarianCommentAlreadyApproved());
					}

					if (parlamentarianComment.getRejected() != null && parlamentarianComment.getRejected()) {
						getView().hideButtons();
						getView().setMessage(applicationMessages.getParlamentarianCommentAlreadyRejected());
					}
				}
				fireEvent(new HideLoadingEvent());
			}
		});
	}

	@Override
	public void approveComment() {
		if (parlamentarianId != null && parlamentarianCommentId != null && parlamentarianCommentKey != null) {
			fireEvent(new ShowLoadingEvent());
			parlamentarianCommentService.approveParlamentarianComment(parlamentarianCommentKey, parlamentarianCommentId, parlamentarianId, new AsyncCallback<ParlamentarianComment>() {

				@Override
				public void onFailure(Throwable caught) {
					fireEvent(new HideLoadingEvent());
					Window.alert(applicationMessages.getErrorParlamentarianCommentApproval());
				}

				@Override
				public void onSuccess(ParlamentarianComment result) {
					fireEvent(new HideLoadingEvent());
					Window.alert(applicationMessages.getParlamentarianCommentApproved());
					placeManager.revealPlace(new PlaceRequest(ParlamentarianPresenter.PLACE).with(PARAM_PARLAMENTARIAN_ID, parlamentarianId.toString()));
				}
			});
		} else {
			Window.alert(applicationMessages.getErrorParlamentarianCommentApproval());
			placeManager.revealPlace(new PlaceRequest(HomePresenter.PLACE));
		}
	}

	@Override
	public void rejectComment() {
		if (parlamentarianId != null && parlamentarianCommentId != null && parlamentarianCommentKey != null) {
			fireEvent(new ShowLoadingEvent());
			parlamentarianCommentService.rejectParlamentarianComment(parlamentarianCommentKey, parlamentarianCommentId, parlamentarianId, new AsyncCallback<ParlamentarianComment>() {

				@Override
				public void onFailure(Throwable caught) {
					fireEvent(new HideLoadingEvent());
					Window.alert(applicationMessages.getErrorParlamentarianCommentRejected());
				}

				@Override
				public void onSuccess(ParlamentarianComment result) {
					fireEvent(new HideLoadingEvent());
					Window.alert(applicationMessages.getParlamentarianCommentRejected());
					placeManager.revealPlace(new PlaceRequest(ParlamentarianPresenter.PLACE).with(PARAM_PARLAMENTARIAN_ID, parlamentarianId.toString()));
				}
			});
		} else {
			Window.alert(applicationMessages.getErrorParlamentarianCommentRejected());
			placeManager.revealPlace(new PlaceRequest(HomePresenter.PLACE));
		}
	}
}
