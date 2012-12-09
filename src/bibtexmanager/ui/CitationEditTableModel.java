package bibtexmanager.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import bibtexmanager.bibtex.InputFormatException;
import bibtexmanager.citation.CitationFactory;
import bibtexmanager.citation.citationtype.Citation;

/**
 * The table model associated with a citation.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
@SuppressWarnings("serial")
public class CitationEditTableModel extends AbstractTableModel {

	// The citation used when initialize the data model.
	// Only used at the beginning, meaning the data used in UI
	// and the data in this citation is not synchronized.
	private Citation citation;

	// Used to store the data in the UI.
	private ArrayList<ValuePair> required;// Required entries.
	private ArrayList<ValuePair> requiredOr;// Required "or" entries.
	private ArrayList<ValuePair> optional;// Other entries.

	/**
	 * Constructor.
	 * 
	 * @param citation
	 *            the citation used in the data model.
	 */
	public CitationEditTableModel(Citation citation) {
		setCitation(citation);
	}

	/**
	 * Change the citation used in the data model.
	 * 
	 * @param citation
	 *            the citation used.
	 */
	public void setCitation(Citation citation) {
		this.citation = citation;
		required = new ArrayList<ValuePair>();
		requiredOr = new ArrayList<ValuePair>();
		optional = new ArrayList<ValuePair>();

		// Error checking.
		if (citation == null)
			return;

		HashMap<String, String> entries = citation.getAllEntries();

		// If the citation contains no entry,
		// Populate the view with the required fields of the citation
		// and set the value of those fields to Citation.Empty_ENTRY.
		if (entries.size() == 0) {
			for (String s : citation.getRequiredEntryNames())
				required.add(new ValuePair(s, Citation.EMPTY_ENTRY));
			for (HashSet<String> set : citation.getRequiredOrEntryNames()) {
				ValuePair pair = new ValuePair(set.iterator().next(),
						Citation.EMPTY_ENTRY);
				pair.orFields = set;
				requiredOr.add(pair);
			}
		}
		// Otherwise just populate the view with all the entries in the
		// citation.
		// Note that "else" is not necessary because when the citation contains
		// no entry,
		// the for loop will not be triggered.
		for (Entry<String, String> pair : entries.entrySet()) {
			if (citation.isRequiredField(pair.getKey()))
				required.add(new ValuePair(pair.getKey(), pair.getValue()));
			else {
				HashSet<String> orFields = citation.isRequiredOrField(pair
						.getKey());
				if (orFields != null) {
					ValuePair orPair = new ValuePair(pair.getKey(),
							pair.getValue());
					orPair.orFields = orFields;
					requiredOr.add(orPair);
				} else
					optional.add(new ValuePair(pair.getKey(), pair.getValue()));
			}
		}
	}

	@Override
	public int getColumnCount() {
		// Only have 2 columns: field name and field value.
		return 2;
	}

	@Override
	public int getRowCount() {
		return required.size() + requiredOr.size() + optional.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		ValuePair pair = null;
		if (row < required.size())
			pair = required.get(row);
		else {
			row = row - required.size();
			if (row < requiredOr.size())
				pair = requiredOr.get(row);
			else {
				row = row - requiredOr.size();
				if (row < optional.size())
					pair = optional.get(row);
			}
		}
		if (pair == null)
			return "";
		if (column == 0)
			return pair.key;
		else if (column == 1)
			return pair.value;
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// All value fields(row == 1) can be edited.
		// A name field(row == 0) is only editable if it is not a required
		// field.
		if (columnIndex != 0)
			return true;
		if (rowIndex >= required.size())
			return true;
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (rowIndex < required.size() && columnIndex == 1)
			required.get(rowIndex).value = (String) aValue;
		else {
			rowIndex -= required.size();
			if (rowIndex < requiredOr.size()) {
				if (columnIndex == 0)
					requiredOr.get(rowIndex).key = (String) aValue;
				else if (columnIndex == 1)
					requiredOr.get(rowIndex).value = (String) aValue;
			} else {
				rowIndex -= requiredOr.size();
				if (columnIndex == 0)
					optional.get(rowIndex).key = (String) aValue;
				else if (columnIndex == 1)
					optional.get(rowIndex).value = (String) aValue;
			}
		}
	}

	/**
	 * Get the cell editor for a specific cell.
	 * 
	 * @param row
	 *            the row number of the cell
	 * @param col
	 *            the column number of the cell
	 * @return a JComboBox editor if the cell is the name field of a required
	 *         "or". Otherwise null.
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		DefaultCellEditor editor = null;
		row -= required.size();
		if (row >= 0 && row < requiredOr.size() && col == 0) {
			JComboBox box = new JComboBox();
			HashSet<String> choices = requiredOr.get(row).orFields;
			for (String s : choices)
				box.addItem(s);
			editor = new DefaultCellEditor(box);
		}
		return editor;
	}

	/**
	 * Add an entry to the data model. Note that the entry added is always
	 * optional because the required fields and required "or" fields are already
	 * there.
	 * 
	 * @param fieldName
	 *            the name of the new entry.
	 * @param fieldValue
	 *            the value of the new entry.
	 */
	public void addEntry(String fieldName, String fieldValue) {
		optional.add(new ValuePair(fieldName, fieldValue));
	}

	/**
	 * Get the data of the model and use it to construct a new citation
	 * 
	 * @return the citation constructed
	 * @throws InputFormatException
	 *             indicating the citation constructed out of UI data is
	 *             invalid.
	 */
	public Citation getCitation() throws InputFormatException {
		Citation result = CitationFactory.createCitation(citation.getType(),
				citation.getKey());
		for (ValuePair p : required)
			result.set(p.key, p.value);
		for (ValuePair p : requiredOr)
			result.set(p.key, p.value);
		for (ValuePair p : optional) {
			if (result.get(p.key).equals(Citation.EMPTY_ENTRY)
					|| result.get(p.key) == null)
				result.set(p.key, p.value);
			else
				throw new InputFormatException(
						"Duplicated field names are not allowed.\nPlease change one of the "
								+ p.key);
		}
		Citation.validateCitation(result);
		return result;
	}

	/**
	 * Remove an entry from the data model. Note that the entry removed must be
	 * optional.
	 * 
	 * @param row
	 *            the row number of the entry to be removed.
	 * @return true if the entry is successfully removed.
	 */
	public boolean removeEntry(int row) {
		// Check whether the entry is optional by looking at its row number.
		int base = required.size() + requiredOr.size();
		if (row < base || row >= base + optional.size())
			return false;
		row -= base;
		optional.remove(row);
		return true;
	}

	/**
	 * A simple data structure used to store the info of an entry.
	 * 
	 * @author Troy Holleman <troyholleman_2015@depauw.edu>
	 * @author Tao Qian <taoqian_2015@depauw.edu>
	 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
	 * @author JD Hoover <johnhoover_2014@depauw.edu>
	 */
	private class ValuePair {
		public String key;// The name of the entry
		public String value;// The value of the entry
		public HashSet<String> orFields;// Only used in required "or" fields. To
										// store other options.

		public ValuePair(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
}
