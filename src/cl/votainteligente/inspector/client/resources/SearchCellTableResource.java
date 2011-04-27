package cl.votainteligente.inspector.client.resources;

import com.google.gwt.user.cellview.client.CellTable;

public interface SearchCellTableResource extends CellTable.Resources {

	public interface Style extends CellTable.Style {
	}

	@Source("cl/votainteligente/inspector/client/resources/css/SearchCellTable.css")
	Style cellTableStyle();

}
