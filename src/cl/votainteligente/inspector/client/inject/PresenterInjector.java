package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.presenters.BillPresenter;
import cl.votainteligente.inspector.client.presenters.HomePresenter;
import cl.votainteligente.inspector.client.presenters.MainPresenter;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(PresenterModule.class)
public interface PresenterInjector extends Ginjector {
	public EventBus getEventBus();
	public MainPresenter getMainPresenter();
	public HomePresenter getHomePresenter();
	public BillPresenter getBillPresenter();
}
