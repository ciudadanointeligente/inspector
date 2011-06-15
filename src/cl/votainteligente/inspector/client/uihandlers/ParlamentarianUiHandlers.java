package cl.votainteligente.inspector.client.uihandlers;

import cl.votainteligente.inspector.shared.NotificationEventType;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ParlamentarianUiHandlers extends UiHandlers {
	void showNotification(String message, NotificationEventType type);
	Boolean getInterestDeclaration();
	Boolean getPatrimonyDeclaration();
}
