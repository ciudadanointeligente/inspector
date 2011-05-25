package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.ParlamentarianServiceAsync;
import cl.votainteligente.inspector.model.*;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import java.util.*;

public class ParlamentarianPresenter extends Presenter<ParlamentarianPresenter.MyView, ParlamentarianPresenter.MyProxy> {
	public static final String PLACE = "parlamentarian";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";

	public interface MyView extends View {
		void clearParlamentarianData();
		void setParlamentarianName(String parlamentarianName);
		void setParlamentarianDescription(String parlamentarianDescription);
		void setParlamentarianBirthDate(String parlamentarianBirthDate);
		void setParlamentarianCivilStatus(String parlamentarianCivilStatus);
		void setParlamentarianSpouse(String parlamentarianSpouse);
		void setParlamentarianChildren(String parlamentarianChildren);
		void setParlamentarianPermanentCommissions(String parlamentarianPermanentCommissions);
		void setParlamentarianSpecialCommissions(String parlamentarianSpecialCommissions);
		void setParlamentarianParty(String parlamentarianParty);
		void setInterestDeclarationLink(String interestDeclarationLink);
		void setPatrimonyDeclarationLink(String patrimonyDeclarationLink);
		CellTable<Society> getSocietyTable();
		void setChartData(Map<String, Double> chartData);
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<ParlamentarianPresenter> {
	}

	@Inject
	private ApplicationMessages applicationMessages;
	@Inject
	private PlaceManager placeManager;
	@Inject
	private ParlamentarianServiceAsync parlamentarianService;
	private Long parlamentarianId;
	private Parlamentarian parlamentarian;

	@Inject
	public ParlamentarianPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void onReset() {
		initSocietyTableColumns();
		getView().clearParlamentarianData();

		if (parlamentarianId != null) {
			getParlamentarian(parlamentarianId);
		}
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);

		try {
			parlamentarianId = Long.parseLong(placeRequest.getParameter(PARAM_PARLAMENTARIAN_ID, null));
		} catch (NumberFormatException nfe) {
			parlamentarianId = null;
		}
	}

	public Long getParlamentarianId() {
		return parlamentarianId;
	}

	public void setParlamentarianId(Long parlamentarianId) {
		this.parlamentarianId = parlamentarianId;
	}

	public void getParlamentarian(Long parlamentarianId) {
		parlamentarianService.getParlamentarian(parlamentarianId, new AsyncCallback<Parlamentarian>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorParlamentarian());
			}

			@Override
			public void onSuccess(Parlamentarian result) {
				parlamentarian = result;

				getView().setParlamentarianName(parlamentarian.toString());

				if (parlamentarian.getParlamentarianType() != null) {
					if (parlamentarian.getDistrict() != null) {
						getView().setParlamentarianDescription(parlamentarian.getParlamentarianType().toString() + " " + applicationMessages.getGeneralBy() + " " + parlamentarian.getDistrict().toString());
					} else {
						getView().setParlamentarianDescription(parlamentarian.getParlamentarianType().toString());
					}
				}

				if (parlamentarian.getBirthDate() != null) {
					getView().setParlamentarianBirthDate(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(parlamentarian.getBirthDate()));
				}

				if (parlamentarian.getCivilStatus() != null) {
					switch (parlamentarian.getCivilStatus()) {
						case SINGLE:
							getView().setParlamentarianCivilStatus(applicationMessages.getCivilStatusSingle());
							break;
						case MARRIED:
							getView().setParlamentarianCivilStatus(applicationMessages.getCivilStatusMarried());
							break;
						case SEPARATED:
							getView().setParlamentarianCivilStatus(applicationMessages.getCivilStatusSeparated());
							break;
						case DIVORCED:
							getView().setParlamentarianCivilStatus(applicationMessages.getCivilStatusDivorced());
							break;
						case WIDOWED:
							getView().setParlamentarianCivilStatus(applicationMessages.getCivilStatusWidowed());
					}
				}

				if (parlamentarian.getSpouse() != null) {
					getView().setParlamentarianSpouse(parlamentarian.getSpouse().toString());
				}

				if (parlamentarian.getChildren() != null && !parlamentarian.getChildren().isEmpty()) {
					StringBuilder sb = new StringBuilder();
					Iterator<Person> iterator = parlamentarian.getChildren().iterator();

					while (iterator.hasNext()) {
						sb.append(iterator.next().getFirstName());

						if (iterator.hasNext()) {
							sb.append(", ");
						} else {
							sb.append(".");
						}
					}

					getView().setParlamentarianChildren(sb.toString());
				}

				if (parlamentarian.getPermanentCommissions() != null && !parlamentarian.getPermanentCommissions().isEmpty()) {
					StringBuilder sb = new StringBuilder();
					Iterator<Commission> iterator = parlamentarian.getPermanentCommissions().iterator();

					while (iterator.hasNext()) {
						sb.append(iterator.next().toString());

						if (iterator.hasNext()) {
							sb.append(", ");
						} else {
							sb.append(".");
						}
					}

					getView().setParlamentarianPermanentCommissions(sb.toString());
				}

				if (parlamentarian.getSpecialCommissions() != null && !parlamentarian.getSpecialCommissions().isEmpty()) {
					StringBuilder sb = new StringBuilder();
					Iterator<Commission> iterator = parlamentarian.getSpecialCommissions().iterator();

					while (iterator.hasNext()) {
						sb.append(iterator.next().toString());

						if (iterator.hasNext()) {
							sb.append(", ");
						} else {
							sb.append(".");
						}
					}

					getView().setParlamentarianSpecialCommissions(sb.toString());
				}

				if (parlamentarian.getParty() != null) {
					getView().setParlamentarianParty(parlamentarian.getParty().toString());
				}

				if (parlamentarian.getInterestDeclarationFile() != null) {
					getView().setInterestDeclarationLink(parlamentarian.getInterestDeclarationFile());
				}

				if (parlamentarian.getPatrimonyDeclarationFile() != null) {
					getView().setPatrimonyDeclarationLink(parlamentarian.getPatrimonyDeclarationFile());
				}

				ListDataProvider<Society> societyData = new ListDataProvider<Society>(new ArrayList<Society>(result.getSocieties().keySet()));
				societyData.addDataDisplay(getView().getSocietyTable());

				Double reportedSocieties = 0d;
				Double unreportedSocieties = 0d;

				for (Boolean reported : parlamentarian.getSocieties().values()) {
					if (reported) {
						reportedSocieties++;
					} else {
						unreportedSocieties++;
					}
				}

				Map<String, Double> chartData = new HashMap<String, Double>();
				chartData.put(applicationMessages.getSocietyReported(), 100d * reportedSocieties / (reportedSocieties + unreportedSocieties));
				chartData.put(applicationMessages.getSocietyUnreported(), 100d * unreportedSocieties / (reportedSocieties + unreportedSocieties));
				getView().setChartData(chartData);
			}
		});
	}

	private void initSocietyTableColumns() {
		CellTable<Society> societyTable = getView().getSocietyTable();

		while (societyTable.getColumnCount() > 0) {
			societyTable.removeColumn(0);
		}

		societyTable.addColumn(new TextColumn<Society>() {
			@Override
			public String getValue(Society society) {
				StringBuilder sb = new StringBuilder();
				Iterator<Category> iterator = society.getCategories().iterator();

				while (iterator.hasNext()) {
					sb.append(iterator.next().getName());

					if (iterator.hasNext()) {
						sb.append(", ");
					} else {
						sb.append('.');
					}
				}

				return sb.toString();
			}
		}, applicationMessages.getSocietyAreaOfInterest());

		societyTable.addColumn(new TextColumn<Society>() {
			@Override
			public String getValue(Society society) {
				return society.getName();
			}
		}, applicationMessages.getSocietyLegalName());

		societyTable.addColumn(new Column<Society, String>(new ImageCell()) {
			@Override
			public String getValue(Society society) {
				Boolean declared = parlamentarian.getSocieties().get(society);

				if (declared != null && declared) {
					return "images/declare_yes.png";
				} else {
					return "images/declare_no.png";
				}
			}
		}, applicationMessages.getSocietyReportedThis());

		societyTable.addColumn(new TextColumn<Society>() {
			@Override
			public String getValue(Society society) {
				for (Category category : society.getCategories()) {
					for (Bill bill : parlamentarian.getAuthoredBills()){
						if (bill.getCategories().contains(category)) {
							return "Sí";
						}
					}

					for (Bill bill : parlamentarian.getVotedBills()){
						if (bill.getCategories().contains(category)) {
							return "Sí";
						}
					}
				}

				return "No";
			}
		}, applicationMessages.getSocietyIsInConflict());

		societyTable.addColumn(new Column<Society, Society>(new ActionCell<Society>("", new ActionCell.Delegate<Society>() {
			@Override
			public void execute(Society society) {
				PlaceRequest placeRequest = new PlaceRequest(SocietyPresenter.PLACE);
				placeManager.revealPlace(placeRequest.with(SocietyPresenter.PARAM_SOCIETY_ID, society.getId().toString()));
			}
		}) {
			@Override
			public void render(Cell.Context context, Society value, SafeHtmlBuilder sb) {
				sb.append(new SafeHtml() {
					@Override
					public String asString() {
						return "<img src=\"images/more.png\"/>";
					}
				});
			}
		}) {
			@Override
			public Society getValue(Society society) {
				return society;
			}
		}, applicationMessages.getSocietyViewMore());
	}
}
