package cl.votainteligente.inspector.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class MultipleInlineHyperLinkCell extends AbstractCell<MultipleInlineHyperLinkCellData> {

	@Override
	public void render(Context context, MultipleInlineHyperLinkCellData value, SafeHtmlBuilder sb) {
		for (InlineHyperLinkCellData cellData : value.getCellData()) {
			sb.append(SafeHtmlUtils.fromTrustedString("<a class=\""))
			.append(SafeHtmlUtils.fromTrustedString((cellData.getStyleNames() == null)? "" : cellData.getStyleNames()))
			.append(SafeHtmlUtils.fromTrustedString("\" "))
			.append(SafeHtmlUtils.fromTrustedString("href=\"#"))
			.append(SafeHtmlUtils.fromTrustedString((cellData.getHref() == null)? "" : cellData.getHref()))
			.append(SafeHtmlUtils.fromTrustedString("\">"))
			.append(SafeHtmlUtils.fromTrustedString((cellData.getValue() == null)? "" : cellData.getValue()))
			.append(SafeHtmlUtils.fromTrustedString("</a>"));
		}
	}
}
