package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.ReportConflictPresenter;
import cl.votainteligente.inspector.client.uihandlers.ReportConflictUiHandlers;
import cl.votainteligente.inspector.model.Parlamentarian;

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

	@UiField ListBox parlamentarianList;
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
	public Long getSelectedParlamentarianId() {
		return new Long(parlamentarianList.getValue(parlamentarianList.getSelectedIndex()));
	}

	@Override
	public void setSelectedParlamentarian(Integer selectedParlamentarianIndex) {
		parlamentarianList.setSelectedIndex(selectedParlamentarianIndex);
	}

	@Override
	public void setupParlamentarianList() {
		parlamentarianList.clear();
		parlamentarianList.addItem("Seleccione un parlamentario");
	}

	@Override
	public void addParlamentarian(Parlamentarian parlamentarian) {
		parlamentarianList.addItem(parlamentarian.getLastName() + ", " + parlamentarian.getFirstName(), parlamentarian.getId().toString());
	}

	@Override
	public String getReport() {
		return report.getText();
	}

	@Override
	public void clearForm() {
		parlamentarianList.setSelectedIndex(0);
		report.setText("");
	}

	@UiHandler("submit")
	void onSubmitClick(ClickEvent event) {
		getUiHandlers().submit();
	}

	@UiHandler("clear")
	void onClearClick(ClickEvent event) {
		clearForm();
	}
}
