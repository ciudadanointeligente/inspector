package cl.votainteligente.inspector.client.views;

import cl.votainteligente.inspector.client.presenters.ParlamentarianPresenter;
import cl.votainteligente.inspector.client.presenters.ParlamentarianPresenterIface;
import cl.votainteligente.inspector.model.Society;

import com.gwtplatform.mvp.client.ViewImpl;

import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.codegen.types.SeriesType;
import org.adapters.highcharts.gwt.widgets.HighChart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.*;

import java.util.Map;

public class ParlamentarianView extends ViewImpl implements ParlamentarianPresenter.MyView {
	private static ParlamentarianViewUiBinder uiBinder = GWT.create(ParlamentarianViewUiBinder.class);
	interface ParlamentarianViewUiBinder extends UiBinder<Widget, ParlamentarianView> {}
	private final Widget widget;

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
	@UiField FlowPanel declarationChartPanel;
	@UiField CellTable<Society> societyTable;

	private ParlamentarianPresenterIface presenter;

	public ParlamentarianView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
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
		declarationChartPanel.clear();
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
		try {
			HighChart declarationChart = new HighChart();
			declarationChart.setAutoResize(true);
			declarationChart.setOption(new OptionPath("/title/text"), "Indice de consistencia");
			declarationChart.setOption(new OptionPath("/subtitle/text"), "Soc. Declaradas v/s No declaradas");
			declarationChart.setOption(new OptionPath("/chart/margin"), new Integer[] {25, 100, 30, 85});
			declarationChart.setOption(new OptionPath("/chart/plotShadow"), false);
			declarationChart.setOption(new OptionPath("/credits/enabled"), false);
			declarationChart.setOption(new OptionPath("/tooltip/enabled"), false);
			declarationChart.setOption(new OptionPath("/plotOptions/pie/animation"), false);
			declarationChart.setOption(new OptionPath("/plotOptions/pie/allowPointSelect"), false);
			declarationChart.setOption(new OptionPath("/plotOptions/pie/dataLabels/enabled"), true);
			declarationChart.setOption(new OptionPath("/plotOptions/pie/dataLabels/color"), "black");
			declarationChart.setOption(new OptionPath("/plotOptions/pie/dataLabels/style/font"), "10px Trebuchet MS, Verdana, sans-serif");

			SeriesType series = new SeriesType("Sociedades");
			series.setType("pie");

			for (String key : chartData.keySet()) {
				series.addEntry(new SeriesType.SeriesDataEntry(key, chartData.get(key)));
			}

			declarationChart.addSeries(series);
			declarationChartPanel.add(declarationChart);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
