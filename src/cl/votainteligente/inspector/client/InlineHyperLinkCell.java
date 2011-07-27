package cl.votainteligente.inspector.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class InlineHyperLinkCell extends AbstractCell<InlineHyperLinkCellData> {

	@Override
	public void render(Context context, InlineHyperLinkCellData value, SafeHtmlBuilder sb) {
		sb.append(SafeHtmlUtils.fromTrustedString("<a class=\""))
		.append(SafeHtmlUtils.fromTrustedString((value.getStyleNames() == null)? "" : value.getStyleNames()))
		.append(SafeHtmlUtils.fromTrustedString("\" "))
		.append(SafeHtmlUtils.fromTrustedString("href=\"#"))
		.append(SafeHtmlUtils.fromTrustedString((value.getHref() == null)? "" : value.getHref()))
		.append(SafeHtmlUtils.fromTrustedString("\">"))
		.append(SafeHtmlUtils.fromTrustedString((value.getValue() == null)? "" : value.getValue()))
		.append(SafeHtmlUtils.fromTrustedString("</a>"));
	}
}
