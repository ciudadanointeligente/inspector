package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.services.*;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class ServiceModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(BillServiceAsync.class).in(Singleton.class);
		bind(BillTypeServiceAsync.class).in(Singleton.class);
		bind(CategoryServiceAsync.class).in(Singleton.class);
		bind(ChamberServiceAsync.class).in(Singleton.class);
		bind(CommissionServiceAsync.class).in(Singleton.class);
		bind(DistrictServiceAsync.class).in(Singleton.class);
		bind(DistrictTypeServiceAsync.class).in(Singleton.class);
		bind(InitiativeTypeServiceAsync.class).in(Singleton.class);
		bind(NotaryServiceAsync.class).in(Singleton.class);
		bind(ParlamentarianServiceAsync.class).in(Singleton.class);
		bind(PartyServiceAsync.class).in(Singleton.class);
		bind(PersonServiceAsync.class).in(Singleton.class);
		bind(SocietyServiceAsync.class).in(Singleton.class);
		bind(SocietyTypeServiceAsync.class).in(Singleton.class);
		bind(StageServiceAsync.class).in(Singleton.class);
		bind(UrgencyServiceAsync.class).in(Singleton.class);
	}
}
