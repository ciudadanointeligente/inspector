package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.ReportConflictPresenter;
import cl.votainteligente.inspector.client.uihandlers.ReportConflictUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class ReportConflictView extends ViewWithUiHandlers<ReportConflictUiHandlers> implements ReportConflictPresenter.MyView {
	private static ReportConflictViewUiBinder uiBinder = GWT.create(ReportConflictViewUiBinder.class);
	interface ReportConflictViewUiBinder extends UiBinder<Widget, ReportConflictView> {}
	private final Widget widget;

	@UiField Label parlamentarianName;
	@UiField TextArea report;
	@UiField Button submit;
	@UiField Button clear;

	public ReportConflictView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setNotification(String message) {
		Window.alert(message);
	}

	@Override
	public void setParlamentarianName(String parlamentarianFullName) {
		parlamentarianName.setText(parlamentarianFullName);
	}

	@Override
	public String getReport() {
		return report.getText();
	}

	@UiHandler("submit")
	void onSubmitClick(ClickEvent event) {
		getUiHandlers().submit();
	}

	@UiHandler("clear")
	void onClearClick(ClickEvent event) {
		report.setText("");
		report.setFocus(true);

	}
}
