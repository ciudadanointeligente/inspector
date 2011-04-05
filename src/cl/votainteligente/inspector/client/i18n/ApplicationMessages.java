package cl.votainteligente.inspector.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface ApplicationMessages extends Messages {

	@Key("general.bill")
	public String getGeneralBill();

	@Key("general.category")
	public String getGeneralCategory();

	@Key("general.district")
	public String getGeneralDistrict();

	@Key("general.parlamentarian")
	public String getGeneralParlamentarian();

	@Key("general.party")
	public String getGeneralParty();

	@Key("general.society")
	public String getGeneralSociety();

	@Key("general.suscribe")
	public String getGeneralSusbcribe();

	@Key("general.profile")
	public String getGeneralProfile();

	@Key("general.title")
	public String getGeneralTitle();

	@Key("error.parlamentarian.list")
	public String getErrorParlamentarianList();

	@Key("error.parlamentarian.search")
	public String getErrorParlamentarianSearch();

	@Key("error.parlamentarian.categorySearch")
	public String getErrorParlamentarianCategorySearch();

	@Key("error.category.list")
	public String getErrorCategoryList();

	@Key("error.category.search")
	public String getErrorCategorySearch();

	@Key("error.category.parlamentarianSearch")
	public String getErrorCategoryParlamentarianSearch();

	@Key("error.bill.list")
	public String getErrorBillList();

	@Key("bill.bulletin")
	public String getBillBulletin();

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
}
