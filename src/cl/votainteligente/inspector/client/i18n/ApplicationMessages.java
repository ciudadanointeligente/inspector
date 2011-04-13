package cl.votainteligente.inspector.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface ApplicationMessages extends Messages {

	@Key("general.bill")
	public String getGeneralBill();

	@Key("general.category")
	public String getGeneralCategory();

	@Key("general.chamber")
	public String getGeneralChamber();

	@Key("general.district")
	public String getGeneralDistrict();

	@Key("general.initiativeType")
	public String getGeneralInitiativeType();

	@Key("general.parlamentarian")
	public String getGeneralParlamentarian();

	@Key("general.parliamentariansInConflict")
	public String getGeneralParlamentariansInConflict();

	@Key("general.party")
	public String getGeneralParty();

	@Key("general.society")
	public String getGeneralSociety();

	@Key("general.societiesInConflict")
	public String getGeneralSocietiesInConflict();

	@Key("general.stage")
	public String getGeneralStage();

	@Key("general.suscribe")
	public String getGeneralSusbcribe();

	@Key("general.profile")
	public String getGeneralProfile();

	@Key("general.title")
	public String getGeneralTitle();

	@Key("general.type")
	public String getGeneralType();

	@Key("general.urgency")
	public String getGeneralUrgency();

	@Key("error.parlamentarian")
	public String getErrorParlamentarian();

	@Key("error.parlamentarian.list")
	public String getErrorParlamentarianList();

	@Key("error.parlamentarian.search")
	public String getErrorParlamentarianSearch();

	@Key("error.parlamentarian.billSearch")
	public String getErrorParlamentarianBillSearch();

	@Key("error.parlamentarian.categorySearch")
	public String getErrorParlamentarianCategorySearch();

	@Key("error.category.list")
	public String getErrorCategoryList();

	@Key("error.category.search")
	public String getErrorCategorySearch();

	@Key("error.category.parlamentarianSearch")
	public String getErrorCategoryParlamentarianSearch();

	@Key("error.society")
	public String getErrorSociety();

	@Key("error.bill")
	public String getErrorBill();

	@Key("error.bill.list")
	public String getErrorBillList();

	@Key("bill.bulletin")
	public String getBillBulletin();

	@Key("bill.entryDate")
	public String getBillEntryDate();

	@Key("bill.originChamber")
	public String getBillOriginChamber();

	@Key("parlamentarian.societies")
	public String getParlamentarianSocieties();

	@Key("parlamentarian.authoredBills")
	public String getParlamentarianAuthoredBills();

	@Key("parlamentarian.votedBills")
	public String getParlamentarianVotedBills();

	@Key("parlamentarian.interestDeclarationFile")
	public String getParlamentarianInterestDeclarationFile();

	@Key("parlamentarian.patrimonyDeclarationFile")
	public String getParlamentarianPatrimonyDeclarationFile();

	@Key("person.civilStatusSingle")
	public String getCivilStatusSingle();

	@Key("person.civilStatusMarried")
	public String getCivilStatusMarried();

	@Key("person.civilStatusSeparated")
	public String getCivilStatusSeparated();

	@Key("person.civilStatusDivorced")
	public String getCivilStatusDivorced();

	@Key("person.civilStatusWidowed")
	public String getCivilStatusWidowed();

	@Key("society.reported")
	public String getSocietyReported();

	@Key("society.unreported")
	public String getSocietyUnreported();
}
