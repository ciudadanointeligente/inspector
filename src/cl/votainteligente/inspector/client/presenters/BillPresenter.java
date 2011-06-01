package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.i18n.ApplicationMessages;
import cl.votainteligente.inspector.client.services.BillServiceAsync;
import cl.votainteligente.inspector.client.services.ParlamentarianServiceAsync;
import cl.votainteligente.inspector.client.services.SocietyServiceAsync;
import cl.votainteligente.inspector.client.uihandlers.BillUiHandlers;
import cl.votainteligente.inspector.model.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import java.util.*;

public class BillPresenter extends Presenter<BillPresenter.MyView, BillPresenter.MyProxy> implements BillUiHandlers {
	public static final String PLACE = "bill";
	public static final String PARAM_BILL_ID = "billId";
	public static final String PARAM_PARLAMENTARIAN_ID = "parlamentarianId";

	public interface MyView extends View, HasUiHandlers<BillUiHandlers> {
		void setBillBulletinNumber(String billBulletinNumber);
		void setBillTitle(String billTitle);
		void setBillDescription(String billContent);
		void setBillEntryDate(Date billEntryDate);
		void setBillInitiativeType(String billInitiativeType);
		void setBillType(String billType);
		void setBillOriginChamber(String billOriginChamber);
		void setBillUrgency(String billUrgency);
		void setBillStage(String billStage);
		void setParlamentarianImage(String parlamentarianImageUrl);
		void setParlamentarianDisplay(String parlamentarianName);
		CellTable<Parlamentarian> getParlamentarianTable();
		void setParlamentarianTable(CellTable<Parlamentarian> parlamentarianTable);
		CellTable<Society> getSocietyTable();
		void setSocietyTable(CellTable<Society> societyTable);
	}

	@ProxyStandard
	@NameToken(PLACE)
	public interface MyProxy extends ProxyPlace<BillPresenter> {
	}

	@Inject
	private ApplicationMessages applicationMessages;
	@Inject
	private PlaceManager placeManager;
	@Inject
	private BillServiceAsync billService;
	@Inject
	private ParlamentarianServiceAsync parlamentarianService;
	@Inject
	private SocietyServiceAsync societyService;

	private Long billId;
	private Long parlamentarianId;
	private Parlamentarian selectedParlamentarian;
	private AbstractDataProvider<Parlamentarian> parlamentarianData;
	private AbstractDataProvider<Society> societyData;

	@Inject
	public BillPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
		parlamentarianData = new ListDataProvider<Parlamentarian>();
		societyData = new ListDataProvider<Society>();
		initParlamentarianTable();
		initSocietyTable();

		if (billId != null) {
			showBill();
		}

		getView().setParlamentarianDisplay(applicationMessages.getGeneralParlamentarian());
		getView().setParlamentarianImage("images/parlamentarian/large/avatar.png");

		if (parlamentarianId != null) {
			loadSelectedParlamentarian();
		} else {
			setSelectedParlamentarian(new Parlamentarian());
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
			billId = Long.parseLong(placeRequest.getParameter(PARAM_BILL_ID, null));
			parlamentarianId = Long.parseLong(placeRequest.getParameter(PARAM_PARLAMENTARIAN_ID, null));
		} catch (NumberFormatException nfe) {
			billId = null;
			parlamentarianId = null;
		}
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getParlamentarianId() {
		return parlamentarianId;
	}

	public void setParlamentarianId(Long parlamentarianId) {
		this.parlamentarianId = parlamentarianId;
	}

	public void setSelectedParlamentarian(Parlamentarian parlamentarian) {
		this.selectedParlamentarian = parlamentarian;
	}

	public Parlamentarian getSelectedParlamentarian() {
		return selectedParlamentarian;
	}

	public void showBill() {
		billService.getBill(billId, new AsyncCallback<Bill>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorBill());
			}

			@Override
			public void onSuccess(Bill result) {
				if (result != null) {
					getView().setBillBulletinNumber(result.getBulletinNumber());
					getView().setBillTitle(result.getTitle());
					getView().setBillDescription(result.getDescription());
					getView().setBillEntryDate(result.getEntryDate());
					getView().setBillInitiativeType(result.getInitiativeType().getName());
					getView().setBillType(result.getBillType().getName());
					getView().setBillOriginChamber(result.getOriginChamber().getName());
					getView().setBillUrgency(result.getUrgency().getName());
					getView().setBillStage(result.getStage().getName());
					getParlamentarians(result);
					getSocieties(result);
				}
			}
		});
	}

	public void loadSelectedParlamentarian() {
		parlamentarianService.getParlamentarian(parlamentarianId, new AsyncCallback<Parlamentarian>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorParlamentarian());
			}

			@Override
			public void onSuccess(Parlamentarian result) {
				setSelectedParlamentarian(result);
				showSelectedParlamentarian();
			}
		});
	}

	public void showSelectedParlamentarian() {
		if (selectedParlamentarian != null) {
			getView().setParlamentarianDisplay(selectedParlamentarian.toString());
			if (selectedParlamentarian.getImage() != null) {
				getView().setParlamentarianImage("images/parlamentarian/large/" + selectedParlamentarian.getImage());
			} else {
				getView().setParlamentarianImage("images/parlamentarian/large/avatar.png");
			}
		}
	}

	public void getParlamentarians(Bill bill) {
		parlamentarianService.getParlamentariansByBill(bill, new AsyncCallback<List<Parlamentarian>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorParlamentarianBillSearch());
			}

			@Override
			public void onSuccess(List<Parlamentarian> result) {
				if (result != null) {
					ListDataProvider<Parlamentarian> data = new ListDataProvider<Parlamentarian>(result);
					setParlamentarianData(data);
				}
			}
		});
	}

	public AbstractDataProvider<Parlamentarian> getParlamentarianData() {
		return parlamentarianData;
	}

	public void setParlamentarianData(AbstractDataProvider<Parlamentarian> data) {
		parlamentarianData = data;
		parlamentarianData.addDataDisplay(getView().getParlamentarianTable());
	}

	public void getSocieties(Bill bill) {
		societyService.getSocietiesByBill(bill, new AsyncCallback<List<Society>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(applicationMessages.getErrorSocietyList());
			}

			@Override
			public void onSuccess(List<Society> result) {
				if (result != null) {
					ListDataProvider<Society> data = new ListDataProvider<Society>(result);
					setSocietyData(data);
				}
			}
		});
	}

	public AbstractDataProvider<Society> getSocietyData() {
		return societyData;
	}

	public void setSocietyData(AbstractDataProvider<Society> data) {
		societyData = data;
		societyData.addDataDisplay(getView().getSocietyTable());
	}

	public void initParlamentarianTable() {
		while (getView().getParlamentarianTable().getColumnCount() > 0) {
			getView().getParlamentarianTable().removeColumn(0);
		}

		// Creates image column
		Column<Parlamentarian, String> imageColumn = new Column<Parlamentarian, String>(new ImageCell()){
			@Override
			public String getValue(Parlamentarian parlamentarian) {

				if (selectedParlamentarian.getImage() != null) {
					return "images/parlamentarian/small/" + selectedParlamentarian.getImage();
				} else {
					return "images/parlamentarian/small/avatar.png";
				}
			}
		};

		// Adds image column to table
		getView().getParlamentarianTable().addColumn(imageColumn, "");

		// Creates name column
		TextColumn<Parlamentarian> nameColumn = new TextColumn<Parlamentarian>() {

			@Override
			public String getValue(Parlamentarian parlamentarian) {
				return parlamentarian.toString();
			}
		};

		// Sets sortable name column
		nameColumn.setSortable(true);
		ListHandler<Parlamentarian> nameSortHandler = new ListHandler<Parlamentarian>(((ListDataProvider<Parlamentarian>) parlamentarianData).getList());
		getView().getParlamentarianTable().addColumnSortHandler(nameSortHandler);
		nameSortHandler.setComparator(nameColumn, new Comparator<Parlamentarian>() {

			@Override
			public int compare(Parlamentarian o1, Parlamentarian o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});

		// Adds name column to table
		getView().getParlamentarianTable().addColumn(nameColumn, applicationMessages.getGeneralParlamentarian());

		// Creates action profile column
		Column<Parlamentarian, Parlamentarian> profileColumn = new Column<Parlamentarian, Parlamentarian>(new ActionCell<Parlamentarian>("", new ActionCell.Delegate<Parlamentarian>() {

			@Override
			public void execute(Parlamentarian parlamentarian) {
				PlaceRequest placeRequest = new PlaceRequest(ParlamentarianPresenter.PLACE);
				placeManager.revealPlace(placeRequest.with(ParlamentarianPresenter.PARAM_PARLAMENTARIAN_ID, parlamentarian.getId().toString()));
			}
		}) {
			@Override
			public void render(Cell.Context context, Parlamentarian value, SafeHtmlBuilder sb) {
				sb.append(new SafeHtml() {

					@Override
					public String asString() {
						return "<div class=\"profileButton\"></div>";
					}
				});
			}
		}) {

			@Override
			public Parlamentarian getValue(Parlamentarian parlamentarian) {
				return parlamentarian;
			}
		};

		// Adds action profile column to table
		getView().getParlamentarianTable().addColumn(profileColumn, applicationMessages.getGeneralProfile());
	}

	public void initSocietyTable() {
		while (getView().getSocietyTable().getColumnCount() > 0) {
			getView().getSocietyTable().removeColumn(0);
		}

		// Creates categories column
		TextColumn<Society> categoriesColumn = new TextColumn<Society>() {

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
		};

		// Adds name column to table
		getView().getSocietyTable().addColumn(categoriesColumn, applicationMessages.getGeneralCategory());

		// Creates name column
		TextColumn<Society> nameColumn = new TextColumn<Society>() {

			@Override
			public String getValue(Society society) {
				return society.getName();
			}
		};

		// Sets sortable name column
		nameColumn.setSortable(true);
		ListHandler<Society> nameSortHandler = new ListHandler<Society>(((ListDataProvider<Society>) societyData).getList());
		getView().getParlamentarianTable().addColumnSortHandler(nameSortHandler);
		nameSortHandler.setComparator(nameColumn, new Comparator<Society>() {

			@Override
			public int compare(Society o1, Society o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// Adds name column to table
		getView().getSocietyTable().addColumn(nameColumn, applicationMessages.getGeneralSocietiesInConflict());

		// Creates reported column
		Column<Society, String> reportedColumn = new Column<Society, String>(new ImageCell()) {

			@Override
			public String getValue(Society society) {
				String reported = "images/declare_no.png";

				if (selectedParlamentarian != null && selectedParlamentarian.getSocieties().containsKey(society)) {
					if (selectedParlamentarian.getSocieties().get(society) == true) {
						reported = "images/declare_yes.png";
					}
				}
				return reported;
			}
		};

		// Adds name reported to table
		getView().getSocietyTable().addColumn(reportedColumn, applicationMessages.getSocietyReported());

		// Creates action profile column
		Column<Society, Society> profileColumn = new Column<Society, Society>(new ActionCell<Society>("", new ActionCell.Delegate<Society>() {

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
						return "<div class=\"glassButton\"></div>";
					}
				});
			}
		}) {

			@Override
			public Society getValue(Society society) {
				return society;
			}
		};

		// Adds action profile column to table
		getView().getSocietyTable().addColumn(profileColumn, applicationMessages.getGeneralViewMore());
	}

	@Override
	public void showParlamentarianProfile() {
		if (selectedParlamentarian != null) {
			PlaceRequest placeRequest = new PlaceRequest(ParlamentarianPresenter.PLACE);
			placeManager.revealPlace(placeRequest.with(ParlamentarianPresenter.PARAM_PARLAMENTARIAN_ID, selectedParlamentarian.getId().toString()));
		}
	}
}
