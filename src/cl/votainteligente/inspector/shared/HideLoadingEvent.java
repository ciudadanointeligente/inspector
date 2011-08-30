package cl.votainteligente.inspector.shared;

import com.google.gwt.event.shared.GwtEvent;

public class HideLoadingEvent extends GwtEvent<HideLoadingEventHandler> {

	public static final Type<HideLoadingEventHandler> TYPE = new Type<HideLoadingEventHandler>();

	public HideLoadingEvent() {
	}

	@Override
	public Type<HideLoadingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HideLoadingEventHandler handler) {
		handler.onHideLoading(this);
	}

}
