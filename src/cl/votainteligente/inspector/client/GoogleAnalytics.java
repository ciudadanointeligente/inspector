package cl.votainteligente.inspector.client;

public class GoogleAnalytics {

	public enum Action {
		VIEW
	}

	public static native void trackHit(String pageName) /*-{
		$wnd._gaq.push(['_trackPageview', pageName]);
	}-*/;

	public static native void trackEvent(String category, Action action, String label, String value) /*-{
		$wnd._gaq.push(['_trackEvent', category, action, label, value]);
	}-*/;
}
