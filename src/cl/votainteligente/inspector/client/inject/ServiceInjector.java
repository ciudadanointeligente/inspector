package cl.votainteligente.inspector.client.inject;

import cl.votainteligente.inspector.client.services.*;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(ServiceModule.class)
public interface ServiceInjector extends Ginjector {
	public BillServiceAsync getBillService();
	public BillTypeServiceAsync getBillTypeService();
	public CategoryServiceAsync getCategoryService();
	public ChamberServiceAsync getChamberService();
	public CommissionServiceAsync getCommissionService();
	public DistrictServiceAsync getDistrictService();
	public DistrictTypeServiceAsync getDistrictTypeService();
	public InitiativeTypeServiceAsync getInitiativeTypeService();
	public NotaryServiceAsync getNotaryService();
	public ParlamentarianServiceAsync getParlamentarianService();
	public PartyServiceAsync getPartyService();
	public PersonServiceAsync getPersonService();
	public SocietyServiceAsync getSocietyService();
	public SocietyTypeServiceAsync getSocietyTypeService();
	public StageServiceAsync getStageService();
	public UrgencyServiceAsync getUrgencyService();
}
