package cl.votainteligente.inspector.shared;

import com.google.gwt.event.shared.GwtEvent;

public class ShowLoadingEvent extends GwtEvent<ShowLoadingEventHandler> {

	public static final Type<ShowLoadingEventHandler> TYPE = new Type<ShowLoadingEventHandler>();

	public ShowLoadingEvent() {
	}

	@Override
	public Type<ShowLoadingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowLoadingEventHandler handler) {
		handler.onShowLoading(this);
	}
}
