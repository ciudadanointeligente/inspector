package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.presenters.*;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;

@GinModules({PresenterModule.class, ServiceModule.class})
public interface MyInjector extends Ginjector {
	ApplicationMessages getApplicationMessages();
	EventBus getEventBus();
	PlaceManager getPlaceManager();
	ProxyFailureHandler getProxyFailureHandler ();

	Provider<MainPresenter> getMainPresenter();
	Provider<PopupPresenter> getPopupPresenter();
	Provider<HomePresenter> getHomePresenter();
	Provider<BillPresenter> getBillPresenter();
	Provider<ParlamentarianPresenter> getParlamentarianPresenter();
	Provider<SocietyPresenter> getSocietyPresenter();
	Provider<SubscriptionPresenter> getSubscriptionPresenter();
}
