package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.presenters.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(PresenterModule.class)
public interface PresenterInjector extends Ginjector {
	public ApplicationMessages getApplicationMessages();
	public EventBus getEventBus();
	public PlaceManager getPlaceManager();
	public MainPresenter getMainPresenter();
	public PopupPresenter getPopupPresenter();
	public HomePresenter getHomePresenter();
	public BillPresenter getBillPresenter();
	public ParlamentarianPresenter getParlamentarianPresenter();
	public SocietyPresenter getSocietyPresenter();
}
