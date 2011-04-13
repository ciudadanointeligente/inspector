package cl.votainteligente.inspector.client.presenters;

import cl.votainteligente.inspector.client.Inspector;
import cl.votainteligente.inspector.model.Person;
import cl.votainteligente.inspector.model.Society;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.Iterator;

public class SocietyPresenter extends WidgetPresenter<SocietyPresenter.Display> implements SocietyPresenterIface {

	public interface Display extends WidgetDisplay {
		void setPresenter(SocietyPresenterIface presenter);
		void clearSocietyData();
		void setSocietyName(String societyName);
		void setSocietyFantasyName(String societyFantasyName);
		void setSocietyUid(String societyUid);
		void setSocietyCreationDate(String societyCreationDate);
		void setSocietyCurrentStock(String societyCurrentStock);
		void setSocietyStatus(String societyStatus);
		void setSocietySubject(String societySubject);
		void setSocietyType(String societyType);
		void setSocietyMembers(String societyMembers);
		void setSocietyInitialStock(String societyInitialStock);
		void setParlamentarianStock(String parlamentarianStock);
		void setSocietyAddress(String societyAddress);
		void setSocietyPublishDate(String societyPublishDate);
		void setNotaryName(String notaryName);
	}

	private Long societyId;
	private Society society;

	@Inject
	public SocietyPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
		display.clearSocietyData();

		if (societyId != null) {
			getSociety(societyId);
		}
	}

	@Override
	public Long getSocietyId() {
		return societyId;
	}

	@Override
	public void setSocietyId(Long societyId) {
		this.societyId = societyId;
	}

	@Override
	public void getSociety(Long societyId) {
		Inspector.getServiceInjector().getSocietyService().getSociety(societyId, new AsyncCallback<Society>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(Inspector.getPresenterInjector().getApplicationMessages().getErrorSociety());
			}

			@Override
			public void onSuccess(Society result) {
				// TODO: parlamentarian data

				society = result;

				if (society.getName() != null) {
					display.setSocietyName(society.getName());
				}

				if (society.getFantasyName() != null) {
					display.setSocietyFantasyName(society.getFantasyName());
				}

				if (society.getUid() != null) {
					display.setSocietyUid(society.getUid());
				}

				if (society.getCreationDate() != null) {
					display.setSocietyCreationDate(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(society.getCreationDate()));
				}

				if (society.getCurrentStock() != null) {
					display.setSocietyCurrentStock(NumberFormat.getCurrencyFormat().format(society.getCurrentStock()));
				}

				if (society.getSocietyStatus() != null) {
					display.setSocietyStatus(society.getSocietyStatus().getName());
				}

				if (society.getSocietyType() != null) {
					display.setSocietyType(society.getSocietyType().getName());
				}

				if (society.getMembers() != null && !society.getMembers().isEmpty()) {
					StringBuilder sb = new StringBuilder();
					Iterator<Person> iterator = society.getMembers().iterator();

					while (iterator.hasNext()) {
						sb.append(iterator.next().toString());

						if (iterator.hasNext()) {
							sb.append(", ");
						} else {
							sb.append(".");
						}
					}

					display.setSocietyMembers(sb.toString());
				}

				if (society.getInitialStock() != null) {
					display.setSocietyInitialStock(NumberFormat.getCurrencyFormat().format(society.getInitialStock()));
				}

				if (society.getAddress() != null) {
					display.setSocietyAddress(society.getAddress());
				}

				if (society.getPublishDate() != null) {
					display.setSocietyPublishDate(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(society.getPublishDate()));
				}

				if (society.getNotary() != null) {
					display.setNotaryName(society.getNotary().getName());
				}
			}
		});
	}

}
