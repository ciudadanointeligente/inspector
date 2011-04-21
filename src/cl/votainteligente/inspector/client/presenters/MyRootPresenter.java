package cl.votainteligente.inspector.client.presenters;

import com.gwtplatform.mvp.client.RootPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MyRootPresenter extends RootPresenter {
	public static final class MyRootView extends RootView {
		@Override
		public void setInSlot(Object slot, Widget content) {
			RootPanel.get("applicationContent").add(content);
		}
	}

	@Inject
	public MyRootPresenter(EventBus eventBus, MyRootView view) {
		super(eventBus, view);
	}
}
