package cl.votainteligente.inspector.client;

public class GoogleAnalytics {

	public enum Action {
		VIEW
	}

	public static native void trackHit(String pageName) /*-{
		$wnd._gaq.push(['_trackPageview', pageName]);
	}-*/;
}
