package cl.votainteligente.inspector.client;

import com.google.gwt.core.client.JavaScriptObject;

public class PieChart {
	private JavaScriptObject chart;

	public native void initChart(JavaScriptObject chartOptions) /*-{
		this.@cl.votainteligente.inspector.client.PieChart::chart = new $wnd.Highcharts.Chart(chartOptions);
	}-*/;
}
