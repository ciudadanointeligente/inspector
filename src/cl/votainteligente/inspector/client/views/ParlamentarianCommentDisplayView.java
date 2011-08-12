package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.ParlamentarianCommentDisplayPresenter;
import cl.votainteligente.inspector.client.uihandlers.ParlamentarianCommentDisplayUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ParlamentarianCommentDisplayView extends ViewWithUiHandlers<ParlamentarianCommentDisplayUiHandlers> implements ParlamentarianCommentDisplayPresenter.MyView {
	private static ParlamentarianCommentDisplayViewUiBinder uiBinder = GWT.create(ParlamentarianCommentDisplayViewUiBinder.class);
	interface ParlamentarianCommentDisplayViewUiBinder extends UiBinder<Widget, ParlamentarianCommentDisplayView> {}
	private final Widget widget;

	@UiField Label close;
	@UiField Label commentSubject;
	@UiField Label commentBody;
	@UiField Label commentCreationDate;

	public ParlamentarianCommentDisplayView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setCommentSubject(String commentSubject) {
		this.commentSubject.setText(commentSubject);
	}

	@Override
	public void setCommentBody(String commentBody) {
		this.commentBody.setText(commentBody);
	}

	@Override
	public void setCommentCreationDate(String commentCreationDate) {
		this.commentCreationDate.setText(commentCreationDate);
	}

	@Override
	public void clear() {
		commentSubject.setText("");
		commentBody.setText("");
		commentCreationDate.setText("");
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		getUiHandlers().close();
	}
}
