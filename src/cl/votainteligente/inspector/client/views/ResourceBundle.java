package cl.votainteligente.inspector.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface ResourceBundle extends ClientBundle {
	public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

	@Source("HomeView.css")
	HomeViewCss HomeView();

	@Source("ParlamentarianView.css")
	ParlamentarianViewCss ParlamentarianView();
}
