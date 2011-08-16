package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.ParlamentarianCommentApprovalPresenter;
import cl.votainteligente.inspector.client.uihandlers.ParlamentarianCommentApprovalUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ParlamentarianCommentApprovalView extends ViewWithUiHandlers<ParlamentarianCommentApprovalUiHandlers> implements ParlamentarianCommentApprovalPresenter.MyView {
	private static ParlamentarianCommentApprovalViewUiBinder uiBinder = GWT.create(ParlamentarianCommentApprovalViewUiBinder.class);
	interface ParlamentarianCommentApprovalViewUiBinder extends UiBinder<Widget, ParlamentarianCommentApprovalView> {}
	private Widget widget;

	@UiField Label commentSubject;
	@UiField Label commentBody;
	@UiField Label commentCreationDate;
	@UiField HTMLPanel messagePanel;
	@UiField Anchor profile;
	@UiField Label message;
	@UiField Button approve;
	@UiField Button reject;

	public ParlamentarianCommentApprovalView() {
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
	public void setMessage(String message) {
		this.messagePanel.setVisible(true);
		this.message.setText(message);
	}

	@Override
	public void setProfileLink(String href) {
		profile.setHref(href);
	}

	@Override
	public void hideButtons() {
		approve.setVisible(false);
		reject.setVisible(false);
	}

	public void showButtons() {
		approve.setVisible(true);
		reject.setVisible(true);
	}

	@Override
	public void clear() {
		commentSubject.setText("");
		commentBody.setText("");
		commentCreationDate.setText("");
		message.setText("");
		messagePanel.setVisible(false);
		showButtons();
	}

	@UiHandler("approve")
	public void onApproveClick(ClickEvent event) {
		getUiHandlers().approveComment();
	}

	@UiHandler("reject")
	public void onRejectClick(ClickEvent event) {
		getUiHandlers().rejectComment();
	}
}
