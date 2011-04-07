package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.inject.PresenterInjector;
import cl.votainteligente.inspector.client.inject.ServiceInjector;
import cl.votainteligente.inspector.model.Bill;
import cl.votainteligente.inspector.model.Category;
import cl.votainteligente.inspector.model.Parlamentarian;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.*;
import com.google.inject.Inject;

import java.util.Comparator;
import java.util.List;

public class HomePresenter extends WidgetPresenter<HomePresenter.Display> implements HomePresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(HomePresenterIface presenter);
		void getParlamentarianSearch();
		void getCategorySearch();
		CellTable<Parlamentarian> getParlamentarianTable();
		CellTable<Category> getCategoryTable();
		CellTable<Bill> getBillTable();
		void setParlamentarianDisplay(String parlamentarianName);
		void setCategoryDisplay(String categoryName);
	}

	private static final ServiceInjector serviceInjector = GWT.create(ServiceInjector.class);
	private static final PresenterInjector presenterInjector = GWT.create(PresenterInjector.class);
	private AbstractDataProvider<Parlamentarian> parlamentarianData;
	private AbstractDataProvider<Category> categoryData;
	private AbstractDataProvider<Bill> billData;
	private Parlamentarian selectedParlamentarian;
	private Category selectedCategory;

	@Inject
	public HomePresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		parlamentarianData = new ListDataProvider<Parlamentarian>();
		categoryData = new ListDataProvider<Category>();
		billData = new ListDataProvider<Bill>();
		selectedParlamentarian = null;
		selectedCategory = null;
		initParlamentarianTable();
		initCategoryTable();
		initBillTable();
		initDataLoad();
		display.setParlamentarianDisplay(presenterInjector.getApplicationMessages().getGeneralParlamentarian());
		display.setCategoryDisplay(presenterInjector.getApplicationMessages().getGeneralCategory());
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	@Override
	public void initDataLoad() {
		serviceInjector.getParlamentarianService().getAllParlamentarians(new AsyncCallback<List<Parlamentarian>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorParlamentarianList());
			}

			@Override
			public void onSuccess(List<Parlamentarian> result) {
				if (result != null) {
					ListDataProvider<Parlamentarian> data = new ListDataProvider<Parlamentarian>(result);
					setParlamentarianData(data);
				}
			}
		});

		serviceInjector.getCategoryService().getAllCategories(new AsyncCallback<List<Category>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorCategoryList());
			}

			@Override
			public void onSuccess(List<Category> result) {
				if (result != null) {
					ListDataProvider<Category> data = new ListDataProvider<Category>(result);
					setCategoryData(data);
				}
			}
		});
		setBillData(new ListDataProvider<Bill>());
	}

	@Override
	public void searchParlamentarian(String keyWord) {
		serviceInjector.getParlamentarianService().searchParlamentarian(keyWord, new AsyncCallback<List<Parlamentarian>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorParlamentarianSearch());
			}

			@Override
			public void onSuccess(List<Parlamentarian> result) {
				if (result != null) {
					ListDataProvider<Parlamentarian> data = new ListDataProvider<Parlamentarian>(result);
					setParlamentarianData(data);
					searchCategory(result);
				}
			}
		});
	}

	@Override
	public void searchParlamentarian(List<Category> categories) {
		serviceInjector.getParlamentarianService().searchParlamentarian(categories, new AsyncCallback<List<Parlamentarian>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorParlamentarianCategorySearch());
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

	@Override
	public void searchCategory(String keyWord) {
		serviceInjector.getCategoryService().searchCategory(keyWord, new AsyncCallback<List<Category>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorCategorySearch());
			}

			@Override
			public void onSuccess(List<Category> result) {
				if (result != null) {
					ListDataProvider<Category> data = new ListDataProvider<Category>(result);
					setCategoryData(data);
					searchParlamentarian(result);
				}
			}
		});
	}

	@Override
	public void searchCategory(List<Parlamentarian> parlamentarians) {
		serviceInjector.getCategoryService().searchCategory(parlamentarians, new AsyncCallback<List<Category>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorCategoryParlamentarianSearch());
			}

			@Override
			public void onSuccess(List<Category> result) {
				if (result != null) {
					ListDataProvider<Category> data = new ListDataProvider<Category>(result);
					setCategoryData(data);
				}
			}
		});
	}

	@Override
	public void searchBill(Parlamentarian parlamentarian, Category category) {
		serviceInjector.getBillService().searchBills(parlamentarian, category, new AsyncCallback<List<Bill>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(presenterInjector.getApplicationMessages().getErrorBillList());
			}

			@Override
			public void onSuccess(List<Bill> result) {
				if (result != null) {
					ListDataProvider<Bill> data = new ListDataProvider<Bill>(result);
					setBillData(data);
				}
			}
		});
	}

	@Override
	public AbstractDataProvider<Parlamentarian> getParlamentarianData() {
		return parlamentarianData;
	}

	@Override
	public void setParlamentarianData(AbstractDataProvider<Parlamentarian> data) {
		parlamentarianData = data;
		parlamentarianData.addDataDisplay(display.getParlamentarianTable());
	}

	@Override
	public AbstractDataProvider<Category> getCategoryData() {
		return categoryData;
	}

	@Override
	public void setCategoryData(AbstractDataProvider<Category> data) {
		categoryData = data;
		categoryData.addDataDisplay(display.getCategoryTable());
	}

	@Override
	public AbstractDataProvider<Bill> getBillData() {
		return billData;
	}

	@Override
	public void setBillData(AbstractDataProvider<Bill> data) {
		billData = data;
		billData.addDataDisplay(display.getBillTable());
	}

	@Override
	public void initParlamentarianTable() {
		// Creates name column
		TextColumn<Parlamentarian> nameColumn = new TextColumn<Parlamentarian>() {
			@Override
			public String getValue(Parlamentarian parlamentarian) {
				return parlamentarian.getFirstName() + " " + parlamentarian.getLastName();
			}
		};

		// Sets sortable name column
		nameColumn.setSortable(true);
		ListHandler<Parlamentarian> nameSortHandler = new ListHandler<Parlamentarian>(((ListDataProvider<Parlamentarian>) parlamentarianData).getList());
		display.getParlamentarianTable().addColumnSortHandler(nameSortHandler);
		nameSortHandler.setComparator(nameColumn, new Comparator<Parlamentarian>() {
			public int compare(Parlamentarian o1, Parlamentarian o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});

		// Adds name column to table
		display.getParlamentarianTable().addColumn(nameColumn, presenterInjector.getApplicationMessages().getGeneralParlamentarian());

		// Creates party column
		TextColumn<Parlamentarian> partyColumn = new TextColumn<Parlamentarian>() {
			@Override
			public String getValue(Parlamentarian parlamentarian) {
				return parlamentarian.getParty().getName();
			}
		};

		// Sets sortable party column
		partyColumn.setSortable(true);
		ListHandler<Parlamentarian> partySortHandler = new ListHandler<Parlamentarian>(((ListDataProvider<Parlamentarian>) parlamentarianData).getList());
		display.getParlamentarianTable().addColumnSortHandler(partySortHandler);
		partySortHandler.setComparator(nameColumn, new Comparator<Parlamentarian>() {
			public int compare(Parlamentarian o1, Parlamentarian o2) {
				return o1.getParty().getName().compareTo(o2.getParty().getName());
			}
		});

		// Adds party column to table
		display.getParlamentarianTable().addColumn(partyColumn, presenterInjector.getApplicationMessages().getGeneralParty());

		// Creates action profile column
		Column<Parlamentarian, Parlamentarian> profileColumn = new Column<Parlamentarian, Parlamentarian>(new ActionCell<Parlamentarian>("", new ActionCell.Delegate<Parlamentarian>() {

			@Override
			public void execute(Parlamentarian parlamentarian) {
				PlaceRequest placeRequest = new PlaceRequest("parlamentarian");
				eventBus.fireEvent(new PlaceRequestEvent(placeRequest.with("parlamentarianId", parlamentarian.getId().toString())));
			}
		}) {
			@Override
			public void render(Cell.Context context, Parlamentarian value, SafeHtmlBuilder sb) {
				sb.append(new SafeHtml() {

					@Override
					public String asString() {
						return "<img src=\"images/explore.png\"/>";
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
		display.getParlamentarianTable().addColumn(profileColumn, presenterInjector.getApplicationMessages().getGeneralProfile());

		// Sets selection model for each row
		final SingleSelectionModel<Parlamentarian> selectionModel = new SingleSelectionModel<Parlamentarian>(Parlamentarian.KEY_PROVIDER);
		display.getParlamentarianTable().setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedParlamentarian = selectionModel.getSelectedObject();
				display.setParlamentarianDisplay(selectedParlamentarian.getFirstName() + " " + selectedParlamentarian.getLastName());
				setBillTable();
			}
		});
	}

	@Override
	public void initCategoryTable() {
		// Creates name column
		TextColumn<Category> nameColumn = new TextColumn<Category>() {
			@Override
			public String getValue(Category category) {
				return category.getName();
			}
		};

		// Sets sortable name column
		nameColumn.setSortable(true);
		ListHandler<Category> sortHandler = new ListHandler<Category>(((ListDataProvider<Category>) categoryData).getList());
		display.getCategoryTable().addColumnSortHandler(sortHandler);
		sortHandler.setComparator(nameColumn, new Comparator<Category>() {
			public int compare(Category o1, Category o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// Adds name column to table
		display.getCategoryTable().addColumn(nameColumn, presenterInjector.getApplicationMessages().getGeneralCategory());

		// Creates action suscription column
		Column<Category, Category> suscriptionColumn = new Column<Category, Category>(new ActionCell<Category>("", new ActionCell.Delegate<Category>() {

			@Override
			public void execute(Category category) {
				// TODO: add category suscription servlet
			}
		}) {
			@Override
			public void render(Cell.Context context, Category value, SafeHtmlBuilder sb) {
				sb.append(new SafeHtml() {

					@Override
					public String asString() {
						return "<img src=\"images/suscribe.png\"/>";
					}
				});
			}
		}) {

			@Override
			public Category getValue(Category category) {
				return category;
			}
		};

		// Adds action suscription column to table
		display.getCategoryTable().addColumn(suscriptionColumn, presenterInjector.getApplicationMessages().getGeneralSusbcribe());

		// Sets selection model for each row
		final SingleSelectionModel<Category> selectionModel = new SingleSelectionModel<Category>(Category.KEY_PROVIDER);
		display.getCategoryTable().setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedCategory = selectionModel.getSelectedObject();
				display.setCategoryDisplay(selectedCategory.getName());
				setBillTable();
			}
		});
	}

	@Override
	public void initBillTable() {
		// Creates bulletin column
		TextColumn<Bill> bulletinColumn = new TextColumn<Bill>() {
			@Override
			public String getValue(Bill bill) {
				return bill.getBulletinNumber();
			}
		};
		// Sets sortable bulletin column
		bulletinColumn.setSortable(true);
		ListHandler<Bill> bulletinSortHandler = new ListHandler<Bill>(((ListDataProvider<Bill>) billData).getList());
		display.getBillTable().addColumnSortHandler(bulletinSortHandler);
		bulletinSortHandler.setComparator(bulletinColumn, new Comparator<Bill>() {
			public int compare(Bill o1, Bill o2) {
				return o1.getBulletinNumber().compareTo(o2.getBulletinNumber());
			}
		});

		// Adds bulletin column to table
		display.getBillTable().addColumn(bulletinColumn, presenterInjector.getApplicationMessages().getBillBulletin());

		// Creates title column
		TextColumn<Bill> titleColumn = new TextColumn<Bill>() {
			@Override
			public String getValue(Bill bill) {
				return bill.getTitle();
			}
		};
		// Sets sortable title column
		titleColumn.setSortable(true);
		ListHandler<Bill> titleSortHandler = new ListHandler<Bill>(((ListDataProvider<Bill>) billData).getList());
		display.getBillTable().addColumnSortHandler(titleSortHandler);
		titleSortHandler.setComparator(titleColumn, new Comparator<Bill>() {
			public int compare(Bill o1, Bill o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});

		// Adds title column to table
		display.getBillTable().addColumn(titleColumn, presenterInjector.getApplicationMessages().getGeneralTitle());

		// Creates action suscription column
		Column<Bill, Bill> suscriptionColumn = new Column<Bill, Bill>(new ActionCell<Bill>("", new ActionCell.Delegate<Bill>() {

			@Override
			public void execute(Bill bill) {
				// TODO: add bill suscription servlet
			}
		}) {
			@Override
			public void render(Cell.Context context, Bill value, SafeHtmlBuilder sb) {
				sb.append(new SafeHtml() {

					@Override
					public String asString() {
						return "<img src=\"images/suscribe.png\"/>";
					}
				});
			}
		}) {

			@Override
			public Bill getValue(Bill bill) {
				return bill;
			}
		};

		// Adds action suscription column to table
		display.getBillTable().addColumn(suscriptionColumn, presenterInjector.getApplicationMessages().getGeneralSusbcribe());
	}

	@Override
	public void setBillTable() {
		if (selectedParlamentarian != null && selectedCategory != null) {
			searchBill(selectedParlamentarian, selectedCategory);
		}
	}
}
