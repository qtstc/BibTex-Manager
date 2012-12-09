package bibtexmanager.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import bibtexmanager.bibtex.InputFormatException;
import bibtexmanager.citation.citationtype.Citation;

/**
 * This is the JTable used to display and edit the entries in citations. We
 * extend JTable because we want to use different Editing method for different
 * cells, e.g. using a ComboBox for the required "or" fields.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
@SuppressWarnings("serial")
public class CitationEditTable extends JTable {

	CitationEditTableModel tableModel;// The data model used to store entry
										// values.

	// Header names.
	private final static String HEADER_FIELD_NAME = "Field Name";
	private final static String HEADER_FIELD_VALUE = "Field Value";

	/**
	 * Constructor. Fill the table with the data of the given citation.
	 * 
	 * @param citation
	 *            the citation associated with the table.
	 */
	public CitationEditTable(Citation citation) {
		tableModel = new CitationEditTableModel(citation);
		setModel(tableModel);
		// Set the header.
		TableColumnModel tcm = getTableHeader().getColumnModel();
		tcm.getColumn(0).setHeaderValue(HEADER_FIELD_NAME);
		tcm.getColumn(1).setHeaderValue(HEADER_FIELD_VALUE);
		// Allow the user to only select one row at a time.
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		// Use different editor for different cells.
		// The details are dealt with by the data model.
		TableCellEditor editor = tableModel.getCellEditor(row, column);
		if (editor != null)
			return editor;
		return super.getCellEditor(row, column);
	}

	/**
	 * Set the citation associated with the table.
	 * 
	 * @param c
	 *            the citation to be associated with the table.
	 */
	public void setCitation(Citation c) {
		stopEditing();
		clearSelection();
		// Change the data model.
		tableModel.setCitation(c);
		repaint();
	}

	/**
	 * Retrieve the field names and field values from the UI. And try to
	 * construct a citation from them.
	 * 
	 * @return the citation constructed
	 * @throws InputFormatException
	 *             indicating that the citation constructed from UI data is
	 *             invalid.
	 */
	public Citation getCitation() throws InputFormatException {
		stopEditing();
		clearSelection();
		return tableModel.getCitation();
	}

	/**
	 * Add an entry to the UI.
	 * 
	 * @param fieldName
	 *            the field name of the entry.
	 * @param fieldValue
	 *            the field value of the entry.
	 */
	public void addEntry(String fieldName, String fieldValue) {
		stopEditing();
		clearSelection();
		tableModel.addEntry(fieldName, fieldValue);
		repaint();
	}

	/**
	 * Attempt to remove an entry from the table. Only work when exactly one
	 * entry is selected, and the entry is not an required entry.
	 * 
	 * @return true if the operation is successful.
	 */
	public boolean removeEntry() {
		stopEditing();
		// Check whether only one entry is selected.
		int[] rows = getSelectedRows();
		if (rows.length != 1)
			return false;
		int row = rows[0];
		boolean result = tableModel.removeEntry(row);
		repaint();
		return result;
	}

	/**
	 * Stop any user input and save the input data to the form.
	 */
	private void stopEditing() {
		if (isEditing()) {
			getCellEditor().stopCellEditing();
		}
	}
}
