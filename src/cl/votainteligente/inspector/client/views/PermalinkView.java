package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.PermalinkPresenter;
import cl.votainteligente.inspector.client.uihandlers.PermalinkUiHandlers;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class PermalinkView extends ViewWithUiHandlers<PermalinkUiHandlers> implements PermalinkPresenter.MyView {
	private static PermalinkViewUiBinder uiBinder = GWT.create(PermalinkViewUiBinder.class);
	interface PermalinkViewUiBinder extends UiBinder<Widget, PermalinkView> {}
	private final Widget widget;

	@UiField FocusPanel permalinkPanel;
	@UiField Label close;
	@UiField TextBox permalink;

	public PermalinkView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setPermalink(String permalink) {
		this.permalink.setText(permalink);
		this.permalink.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				PermalinkView.this.permalink.selectAll();
			}
		});
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		getUiHandlers().close();
	}
}
