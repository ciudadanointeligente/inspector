package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.SocietyPresenter;
import cl.votainteligente.inspector.client.uihandlers.SocietyUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SocietyView extends ViewWithUiHandlers<SocietyUiHandlers> implements SocietyPresenter.MyView {
	private static SocietyViewUiBinder uiBinder = GWT.create(SocietyViewUiBinder.class);
	interface SocietyViewUiBinder extends UiBinder<Widget, SocietyView> {}
	private final Widget widget;

	@UiField Label societyName;
	@UiField Label societyFantasyName;
	@UiField Label societyUid;
	@UiField Label societyCreationDate;
	@UiField Label societyCurrentStock;
	@UiField Label societyStatus;
	@UiField Label societySubject;
	@UiField Label societyType;
	@UiField Label societyMembers;
	@UiField Label societyInitialStock;
	@UiField Label parlamentarianStock;
	@UiField Label societyAddress;
	@UiField Label societyPublishDate;
	@UiField Label notaryName;
	@UiField Label close;

	public SocietyView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void clearSocietyData() {
		societyName.setText("");
		societyFantasyName.setText("");
		societyUid.setText("");
		societyCreationDate.setText("");
		societyCurrentStock.setText("");
		societyStatus.setText("");
		societySubject.setText("");
		societyType.setText("");
		societyMembers.setText("");
		societyInitialStock.setText("");
		parlamentarianStock.setText("");
		societyAddress.setText("");
		societyPublishDate.setText("");
		notaryName.setText("");
	}

	@Override
	public void setSocietyName(String societyName) {
		this.societyName.setText(societyName);
	}

	@Override
	public void setSocietyFantasyName(String societyFantasyName) {
		this.societyFantasyName.setText(societyFantasyName);
	}

	@Override
	public void setSocietyUid(String societyUid) {
		this.societyUid.setText(societyUid);
	}

	@Override
	public void setSocietyCreationDate(String societyCreationDate) {
		this.societyCreationDate.setText(societyCreationDate);
	}

	@Override
	public void setSocietyCurrentStock(String societyCurrentStock) {
		this.societyCurrentStock.setText(societyCurrentStock);
	}

	@Override
	public void setSocietyStatus(String societyStatus) {
		this.societyStatus.setText(societyStatus);
	}

	@Override
	public void setSocietySubject(String societySubject) {
		this.societySubject.setText(societySubject);
	}

	@Override
	public void setSocietyType(String societyType) {
		this.societyType.setText(societyType);
	}

	@Override
	public void setSocietyMembers(String societyMembers) {
		this.societyMembers.setText(societyMembers);
	}

	@Override
	public void setSocietyInitialStock(String societyInitialStock) {
		this.societyInitialStock.setText(societyInitialStock);
	}

	@Override
	public void setParlamentarianStock(String parlamentarianStock) {
		this.parlamentarianStock.setText(parlamentarianStock);
	}

	@Override
	public void setSocietyAddress(String societyAddress) {
		this.societyAddress.setText(societyAddress);
	}

	@Override
	public void setSocietyPublishDate(String societyPublishDate) {
		this.societyPublishDate.setText(societyPublishDate);
	}

	@Override
	public void setNotaryName(String notaryName) {
		this.notaryName.setText(notaryName);
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		getUiHandlers().close();
	}
}
