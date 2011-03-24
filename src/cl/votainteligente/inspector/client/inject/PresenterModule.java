package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.presenters.BillPresenter;
import cl.votainteligente.inspector.client.presenters.HomePresenter;
import cl.votainteligente.inspector.client.presenters.MainPresenter;
import cl.votainteligente.inspector.client.views.BillView;
import cl.votainteligente.inspector.client.views.HomeView;
import cl.votainteligente.inspector.client.views.MainView;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

import com.google.inject.Singleton;

public class PresenterModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
		bindPresenter(MainPresenter.class, MainPresenter.Display.class, MainView.class);
		bindPresenter(HomePresenter.class, HomePresenter.Display.class, HomeView.class);
		bindPresenter(BillPresenter.class, BillPresenter.Display.class, BillView.class);
	}
}
