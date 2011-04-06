package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.inject.PresenterInjector;
import cl.votainteligente.inspector.client.inject.ServiceInjector;
import cl.votainteligente.inspector.model.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
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

public class ParlamentarianPresenter extends WidgetPresenter<ParlamentarianPresenter.Display> implements ParlamentarianPresenterIface {

	public interface Display extends WidgetDisplay {
		void setPresenter(ParlamentarianPresenterIface presenter);
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

	private static final ServiceInjector serviceInjector = GWT.create(ServiceInjector.class);
	private static final PresenterInjector presenterInjector = GWT.create(PresenterInjector.class);
	private Long parlamentarianId;
	private Parlamentarian parlamentarian;

	@Inject
	public ParlamentarianPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		initSocietyTableColumns();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
		display.clearParlamentarianData();

		if (parlamentarianId != null) {
			getParlamentarian(parlamentarianId);
		}
	}

	@Override
	public Long getParlamentarianId() {
		return parlamentarianId;
	}

	@Override
	public void setParlamentarianId(Long parlamentarianId) {
		this.parlamentarianId = parlamentarianId;
	}

	@Override
	public void getParlamentarian(Long parlamentarianId) {
		serviceInjector.getParlamentarianService().getParlamentarian(parlamentarianId, new AsyncCallback<Parlamentarian>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("No se ha podido cargar la informacion del parlamentario");
			}

			@Override
			public void onSuccess(Parlamentarian result) {
				parlamentarian = result;

				display.setParlamentarianName(parlamentarian.toString());

				if (parlamentarian.getParlamentarianType() != null) {
					if (parlamentarian.getDistrict() != null) {
						display.setParlamentarianDescription(parlamentarian.getParlamentarianType().toString() + " por " + parlamentarian.getDistrict().toString());
					} else {
						display.setParlamentarianDescription(parlamentarian.getParlamentarianType().toString());
					}
				}

				if (parlamentarian.getBirthDate() != null) {
					display.setParlamentarianBirthDate(DateTimeFormat.getFormat("dd/MM/yyyy").format(parlamentarian.getBirthDate()));
				}

				if (parlamentarian.getCivilStatus() != null) {
					switch (parlamentarian.getCivilStatus()) {
						case SINGLE:
							display.setParlamentarianCivilStatus(presenterInjector.getApplicationMessages().getCivilStatusSingle());
							break;
						case MARRIED:
							display.setParlamentarianCivilStatus(presenterInjector.getApplicationMessages().getCivilStatusMarried());
							break;
						case SEPARATED:
							display.setParlamentarianCivilStatus(presenterInjector.getApplicationMessages().getCivilStatusSeparated());
							break;
						case DIVORCED:
							display.setParlamentarianCivilStatus(presenterInjector.getApplicationMessages().getCivilStatusDivorced());
							break;
						case WIDOWED:
							display.setParlamentarianCivilStatus(presenterInjector.getApplicationMessages().getCivilStatusWidowed());
					}
				}

				if (parlamentarian.getSpouse() != null) {
					display.setParlamentarianSpouse(parlamentarian.getSpouse().toString());
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

					display.setParlamentarianChildren(sb.toString());
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

					display.setParlamentarianPermanentCommissions(sb.toString());
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

					display.setParlamentarianSpecialCommissions(sb.toString());
				}

				if (parlamentarian.getParty() != null) {
					display.setParlamentarianParty(parlamentarian.getParty().toString());
				}

				if (parlamentarian.getInterestDeclarationFile() != null) {
					display.setInterestDeclarationLink(parlamentarian.getInterestDeclarationFile());
				}

				if (parlamentarian.getPatrimonyDeclarationFile() != null) {
					display.setPatrimonyDeclarationLink(parlamentarian.getPatrimonyDeclarationFile());
				}

				ListDataProvider<Society> societyData = new ListDataProvider<Society>(new ArrayList<Society>(result.getSocieties().keySet()));
				societyData.addDataDisplay(display.getSocietyTable());

				Double declaredSocieties = 0d;
				Double undeclaredSocieties = 0d;

				for (Boolean declared : parlamentarian.getSocieties().values()) {
					if (declared) {
						declaredSocieties++;
					} else {
						undeclaredSocieties++;
					}
				}

				Map<String, Double> chartData = new HashMap<String, Double>();
				chartData.put("Declaradas", 100d * declaredSocieties / (declaredSocieties + undeclaredSocieties));
				chartData.put("No declaradas", 100d * undeclaredSocieties / (declaredSocieties + undeclaredSocieties));
				display.setChartData(chartData);
			}
		});
	}

	private void initSocietyTableColumns() {
		CellTable<Society> societyTable = display.getSocietyTable();

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
		}, "Area de Interés");

		societyTable.addColumn(new TextColumn<Society>() {
			@Override
			public String getValue(Society society) {
				return society.getName();
			}
		}, "Razón Social");

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
		}, "La declara?");

		societyTable.addColumn(new Column<Society, Society>(new ActionCell<Society>("", new ActionCell.Delegate<Society>() {
			@Override
			public void execute(Society society) {
				PlaceRequest placeRequest = new PlaceRequest("society");
				eventBus.fireEvent(new PlaceRequestEvent(placeRequest.with("societyId", society.getId().toString())));
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
		}, "Ver +");
	}
}
