package cl.votainteligente.inspector.client.presenters;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;

public class PopupPresenter extends Presenter<PopupPresenter.MyView, PopupPresenter.MyProxy> {
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_POPUP_CONTENT = new Type<RevealContentHandler<?>>();

	public interface MyView extends View {
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<PopupPresenter> {
	}

	@Inject
	public PopupPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealRootContentEvent(this));
	}
}
