package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.places.BasicPlaceManager;
import cl.votainteligente.inspector.client.presenters.*;
import cl.votainteligente.inspector.client.views.*;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.ParameterTokenFormatter;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;

import com.google.inject.Singleton;

public class PresenterModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bind(ApplicationMessages.class).in(Singleton.class);
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class);
		bind(PlaceManager.class).to(BasicPlaceManager.class).asEagerSingleton();
		bindPresenter(MainPresenter.class, MainPresenter.Display.class, MainView.class);
		bindPresenter(HomePresenter.class, HomePresenter.Display.class, HomeView.class);
		bindPresenter(BillPresenter.class, BillPresenter.Display.class, BillView.class);
		bindPresenter(ParlamentarianPresenter.class, ParlamentarianPresenter.Display.class, ParlamentarianView.class);
		bindPresenter(SocietyPresenter.class, SocietyPresenter.Display.class, SocietyView.class);
	}
}
