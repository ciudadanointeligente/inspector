package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.places.MyPlaceManager;
import cl.votainteligente.inspector.client.presenters.*;
import cl.votainteligente.inspector.client.views.*;

import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;

public class PresenterModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bind(ApplicationMessages.class).asEagerSingleton();
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
		bind(RootPresenter.class).to(MyRootPresenter.class).asEagerSingleton();
		bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);
		bindPresenter(MainPresenter.class, MainPresenter.MyView.class, MainView.class, MainPresenter.MyProxy.class);
		bindPresenter(PopupPresenter.class, PopupPresenter.MyView.class, PopupView.class, PopupPresenter.MyProxy.class);
		bindPresenter(HomePresenter.class, HomePresenter.MyView.class, HomeView.class, HomePresenter.MyProxy.class);
		bindPresenter(BillPresenter.class, BillPresenter.MyView.class, BillView.class, BillPresenter.MyProxy.class);
		bindPresenter(ParlamentarianPresenter.class, ParlamentarianPresenter.MyView.class, ParlamentarianView.class, ParlamentarianPresenter.MyProxy.class);
		bindPresenter(ParlamentarianCommentPresenter.class, ParlamentarianCommentPresenter.MyView.class, ParlamentarianCommentView.class, ParlamentarianCommentPresenter.MyProxy.class);
		bindPresenter(PermalinkPresenter.class, PermalinkPresenter.MyView.class, PermalinkView.class, PermalinkPresenter.MyProxy.class);
		bindPresenter(SocietyPresenter.class, SocietyPresenter.MyView.class, SocietyView.class, SocietyPresenter.MyProxy.class);
		bindPresenter(StockPresenter.class, StockPresenter.MyView.class, StockView.class, StockPresenter.MyProxy.class);
		bindPresenter(SubscriptionPresenter.class, SubscriptionPresenter.MyView.class, SubscriptionView.class, SubscriptionPresenter.MyProxy.class);
		bindPresenter(UnsubscribePresenter.class, UnsubscribePresenter.MyView.class, UnsubscribeView.class, UnsubscribePresenter.MyProxy.class);
	}
}
