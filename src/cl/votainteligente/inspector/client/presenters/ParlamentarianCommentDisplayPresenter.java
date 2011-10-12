package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.ParlamentarianCommentServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.ParlamentarianCommentDisplayUiHandlers;
import cl.votainteligente.inspector.model.ParlamentarianComment;
import cl.votainteligente.inspector.shared.NotificationEvent;
import cl.votainteligente.inspector.shared.NotificationEventParams;
import cl.votainteligente.inspector.shared.NotificationEventType;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ParlamentarianCommentDisplayPresenter extends Presenter<ParlamentarianCommentDisplayPresenter.MyView, ParlamentarianCommentDisplayPresenter.MyProxy> implements ParlamentarianCommentDisplayUiHandlers {
	public static final String PLACE = "parlamentarianCommentDisplay";
	public static final String PARAM_PARLAMENTARIAN_COMMENT_ID = "parlamentarianCommentId";
	public static final Integer PARLAMENTARIAN_COMMENT_SUBJECT_INIT = 0;
	public static final Integer PARLAMENTARIAN_COMMENT_SUBJECT_SIZE = 40;

	public interface MyView extends View, HasUiHandlers<ParlamentarianCommentDisplayUiHandlers> {
		void setCommentSubject(String commentSubject);
		void setCommentBody(String commentBody);
		void setCommentCreationDate(String commentCreationDate);
		void clear();
	}

	@Inject
	ApplicationMessages applicationMessages;
	@Inject
	private ParlamentarianCommentServiceAsync parlamentarianCommentService;
	private Long parlamentarianCommentId;
	private ParlamentarianComment parlamentarianComment;

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<ParlamentarianCommentDisplayPresenter> {
	}

	@Inject
	public ParlamentarianCommentDisplayPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
	}

	@Override
	protected void onReveal() {
		getView().clear();
		if (parlamentarianCommentId != null) {
			getParlamentarianComment(parlamentarianCommentId);
		} else {
			NotificationEventParams params = new NotificationEventParams();
			params.setMessage(applicationMessages.getErrorParlamentarianComment());
			params.setType(NotificationEventType.ERROR);
			params.setDuration(NotificationEventParams.DURATION_SHORT);
			fireEvent(new NotificationEvent(params));
			close();
		}
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_POPUP_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		try {
			parlamentarianCommentId = Long.parseLong(placeRequest.getParameter(PARAM_PARLAMENTARIAN_COMMENT_ID, null));
		} catch (NumberFormatException nfe) {
			parlamentarianCommentId = null;
		}
	}

	public void getParlamentarianComment(Long parlamentarianCommentId) {
		parlamentarianCommentService.getParlamentarianComment(parlamentarianCommentId, new AsyncCallback<ParlamentarianComment>() {

			@Override
			public void onFailure(Throwable caught) {
				NotificationEventParams params = new NotificationEventParams();
				params.setMessage(applicationMessages.getErrorParlamentarianComment());
				params.setType(NotificationEventType.ERROR);
				params.setDuration(NotificationEventParams.DURATION_SHORT);
				fireEvent(new NotificationEvent(params));
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

					String subject = parlamentarianComment.getSubject();
					Window.setTitle(applicationMessages.getGeneralWindowTitle((subject.length() > PARLAMENTARIAN_COMMENT_SUBJECT_SIZE)? subject.substring(PARLAMENTARIAN_COMMENT_SUBJECT_INIT, PARLAMENTARIAN_COMMENT_SUBJECT_SIZE): subject, applicationMessages.getGeneralParlamentarianCommentViewTitle(), applicationMessages.getGeneralAppName()));

				} else {
					NotificationEventParams params = new NotificationEventParams();
					params.setMessage(applicationMessages.getErrorParlamentarianComment());
					params.setType(NotificationEventType.ERROR);
					params.setDuration(NotificationEventParams.DURATION_SHORT);
					fireEvent(new NotificationEvent(params));
					History.back();
				}
			}
		});
	}

	@Override
	public void close() {
		History.back();
	}
}
