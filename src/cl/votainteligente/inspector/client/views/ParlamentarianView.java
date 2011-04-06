package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.PieChart;
import cl.votainteligente.inspector.client.presenters.ParlamentarianPresenter;
import cl.votainteligente.inspector.client.presenters.ParlamentarianPresenterIface;
import cl.votainteligente.inspector.model.Society;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.*;

import java.util.Map;

public class ParlamentarianView extends Composite implements ParlamentarianPresenter.Display {

	private static ParlamentarianViewUiBinder uiBinder = GWT.create(ParlamentarianViewUiBinder.class);

	interface ParlamentarianViewUiBinder extends UiBinder<Widget, ParlamentarianView> {
	}

	@UiField Label parlamentarianName;
	@UiField Label parlamentarianDescription;
	@UiField Label parlamentarianBirthDate;
	@UiField Label parlamentarianCivilStatus;
	@UiField Label parlamentarianSpouse;
	@UiField Label parlamentarianChildren;
	@UiField Label parlamentarianPermanentCommissions;
	@UiField Label parlamentarianSpecialCommissions;
	@UiField Label parlamentarianParty;
	@UiField Anchor interestDeclarationLink;
	@UiField Anchor patrimonyDeclarationLink;
	@UiField CellTable<Society> societyTable;
	private PieChart pieChart;

	private ParlamentarianPresenterIface presenter;

	public ParlamentarianView() {
		initWidget(uiBinder.createAndBindUi(this));
		pieChart = new PieChart();
	}

	@Override
	public void setPresenter(ParlamentarianPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void clearParlamentarianData() {
		parlamentarianName.setText("");
		parlamentarianDescription.setText("");
		parlamentarianBirthDate.setText("");
		parlamentarianCivilStatus.setText("");
		parlamentarianSpouse.setText("");
		parlamentarianChildren.setText("");
		parlamentarianPermanentCommissions.setText("");
		parlamentarianSpecialCommissions.setText("");
		parlamentarianParty.setText("");
		interestDeclarationLink.setHref("");
		patrimonyDeclarationLink.setHref("");
	}

	@Override
	public void setParlamentarianName(String parlamentarianName) {
		this.parlamentarianName.setText(parlamentarianName);
	}

	@Override
	public void setParlamentarianDescription(String parlamentarianDescription) {
		this.parlamentarianDescription.setText(parlamentarianDescription);
	}

	@Override
	public void setParlamentarianBirthDate(String parlamentarianBirthDate) {
		this.parlamentarianBirthDate.setText(parlamentarianBirthDate);
	}

	@Override
	public void setParlamentarianCivilStatus(String parlamentarianCivilStatus) {
		this.parlamentarianCivilStatus.setText(parlamentarianCivilStatus);
	}

	@Override
	public void setParlamentarianSpouse(String parlamentarianSpouse) {
		this.parlamentarianSpouse.setText(parlamentarianSpouse);
	}

	@Override
	public void setParlamentarianChildren(String parlamentarianChildren) {
		this.parlamentarianChildren.setText(parlamentarianChildren);
	}

	@Override
	public void setParlamentarianPermanentCommissions(String parlamentarianPermanentCommissions) {
		this.parlamentarianPermanentCommissions.setText(parlamentarianPermanentCommissions);
	}

	@Override
	public void setParlamentarianSpecialCommissions(String parlamentarianSpecialCommissions) {
		this.parlamentarianSpecialCommissions.setText(parlamentarianSpecialCommissions);
	}

	@Override
	public void setParlamentarianParty(String parlamentarianParty) {
		this.parlamentarianParty.setText(parlamentarianParty);
	}

	@Override
	public void setInterestDeclarationLink(String interestDeclarationLink) {
		this.interestDeclarationLink.setHref(interestDeclarationLink);
	}

	@Override
	public void setPatrimonyDeclarationLink(String patrimonyDeclarationLink) {
		this.patrimonyDeclarationLink.setHref(patrimonyDeclarationLink);
	}

	@Override
	public CellTable<Society> getSocietyTable() {
		return societyTable;
	}

	@Override
	public void setChartData(Map<String, Double> chartData) {
		pieChart.initChart(getChartOptions(getJsData(chartData)));
	}

	private JavaScriptObject getJsData(Map<String, Double> chartData) {
		JsArrayMixed jsData = JsArrayMixed.createArray().cast();

		for (String key : chartData.keySet()) {
			JsArrayMixed value = JsArrayMixed.createArray().cast();
			value.push(key);
			value.push(chartData.get(key));
			jsData.push(value);
		}

		return jsData;
	};

	private native JavaScriptObject getChartOptions(JavaScriptObject chartData) /*-{
		var options = {
			chart: {
				renderTo: 'parlamentarianDeclarationChart',
				margin: [25, 100, 30, 85],
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: 'Indice de consistencia: Soc. Declaradas v/s No declaradas'
			},
			tooltip: {
				formatter: function() {
					return '<b>' + this.point.name + '</b>: ' + this.y + '%';
				}
			},
			plotOptions: {
				pie: {
					animation: false,
					allowPointSelect: false,
					dataLabels: {
						enabled: true,
						formatter: function() {
							return this.point.name;
						},
						color: 'black',
						style: {
							font: '10px Trebuchet MS, Verdana, sans-serif'
						}
					}
				}
			},
			series: [{
				type: 'pie',
				name: 'sociedades',
				data: chartData
			}],
			credits: {
				enabled: false
			}
		};

		return options;
	}-*/;
}
