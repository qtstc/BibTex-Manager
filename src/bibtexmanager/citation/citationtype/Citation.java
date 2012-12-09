package bibtexmanager.citation.citationtype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import bibtexmanager.bibtex.InputFormatException;
import bibtexmanager.citation.CitationFactory;

/**
 * This is the super class for all citations. All other citation sub classes
 * must implement this class. This class basically stores all the entries that
 * are in a specific citation.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public abstract class Citation implements Cloneable {

	private String key; // a name for the citation itself
	protected String type;// The type of the citation

	// Stores all the entries with the key being the name and value being the
	// value.
	protected HashMap<String, String> entries;

	// Stores the name of all required entries. A valid citation must contains
	// all required entries.
	protected HashSet<String> requiredEntries;

	// Stores the names of required "or" entries. A valid citation must contain
	// as least one entry from each HashSet.
	protected ArrayList<HashSet<String>> requiredOrEntries;

	public final static String EMPTY_ENTRY = "";
	private final static String SPECIAL_CHARACTERS = "+_:-/.";

	/**
	 * Construct a new citation instance.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	protected Citation(String key) {
		entries = new HashMap<String, String>();
		requiredEntries = new HashSet<String>();
		requiredOrEntries = new ArrayList<HashSet<String>>();
		setKey(key);
		setType();
		initializeRequiredEntries();
	}

	/**
	 * Set the given field of the citation to the given value.
	 * 
	 * @param fieldName
	 *            the name of the field, value to be taken from
	 * @param fieldValue
	 * @return Whether the assignment is successful
	 */
	public Boolean set(String fieldName, String fieldValue) {
		entries.put(fieldName, fieldValue);
		// Return a boolean because may need to check whether the fieldName and
		// fieldValue is valid. Now does not do anything.
		return true;
	}

	/**
	 * Get the value of the entry with the given name.
	 * 
	 * @param fieldName
	 *            the name of the entry
	 * @return the value of the entry if it is found. Otherwise returns
	 *         EMPTY_ENTRY.
	 */
	public String get(String fieldName) {
		String temp = entries.get(fieldName);
		if (temp == null)
			return EMPTY_ENTRY;
		return temp;
	}

	/**
	 * Set the key of the citation.
	 * 
	 * @param key
	 *            the new key of the citation.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Get the key of the citation
	 * 
	 * @return the key of the citation
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get the type of the citation
	 * 
	 * @return the type of the citation
	 */
	public String getType() {
		return type;
	}

	/**
	 * returns a string representation of the citation
	 * 
	 * @return a string representation of the citation
	 */
	public String toStringDetailed() {
		String result = "@" + type + "{" + key;
		for (Entry<String, String> e : entries.entrySet()) {
			result += ",\n\t" + e.getKey() + " = \"" + e.getValue() + "\"";
		}
		result += "\n}";
		return result;
	}

	public String toString() {
		return type + "     " + key;
	}

	/**
	 * A helper method used to add required entry names to the requiredEntries
	 * HashMap.
	 * 
	 * @param required
	 *            the names of the entries required. A valid citation needs to
	 *            contain all entries listed.
	 */
	protected void addRequiredEntries(String... required) {
		for (String s : required)
			requiredEntries.add(s);
	}

	/**
	 * A helper method used to add required "or" entry names to the
	 * requiredOrEntries ArrayList.
	 * 
	 * @param requiredOr
	 *            the names of the required "or" entries. A valid citation needs
	 *            to contain at least one of the entries listed.
	 */
	protected void addRequiredOrEntries(String... requiredOr) {
		HashSet<String> orSet = new HashSet<String>();
		for (String s : requiredOr)
			orSet.add(s);
		requiredOrEntries.add(orSet);
	}

	/**
	 * Check whether the citation is valid. To be called only after all entry
	 * values are added to the citation.
	 * 
	 * @return true if the citation contains all the required entries.
	 */
	public boolean hasRequiredFields() {
		for (String s : requiredEntries) {
			if (!entries.containsKey(s))
				return false;
			// else if (entries.get(s).trim().equals(EMPTY_ENTRY))
			// return false;
		}

		for (int i = 0; i < requiredOrEntries.size(); i++) {
			boolean contains = false;
			for (String s : requiredOrEntries.get(i)) {
				if (entries.containsKey(s)) {
					// if (!entries.get(s).trim().equals(EMPTY_ENTRY))
					contains = true;
				}
			}
			if (!contains)
				return false;
		}
		return true;
	}

	/**
	 * Return a list of the values of all the fields in this citation. Key and
	 * Type are not included. This is primarily used for searching.
	 * 
	 * @return an ArrayList containing all the values.
	 */
	public ArrayList<String> getAllFieldValue() {
		// Here we did not return entries.values() because modification to the
		// collection will be reflected in the original HashMap.
		// Clone is another choice.
		ArrayList<String> result = new ArrayList<String>();
		for (String s : entries.values()) {
			result.add(s);
		}
		return result;
	}

	/**
	 * Add the names of the required entries to the requiredEntries HashMap and
	 * requiredOrEntries ArrayList. Using the addRequiredEntries() and
	 * addRequiredOrEntries() methods is recommended.
	 */
	protected abstract void initializeRequiredEntries();

	/**
	 * Set the type of the particular citation. Type names to be taken from
	 * TypeName.
	 */
	protected abstract void setType();

	/**
	 * Get all the names of all the entries required by this citation.
	 * 
	 * @return an ArrayList of entry names.
	 */
	public ArrayList<String> getRequiredEntryNames() {
		return new ArrayList<String>(requiredEntries);
	}

	/**
	 * Get all the names of all the OR entries required by this citation.
	 * 
	 * @return an ArrayList of entry names.
	 */
	public ArrayList<HashSet<String>> getRequiredOrEntryNames() {
		return requiredOrEntries;
	}

	/**
	 * Get all the entries stored in this citation.
	 * 
	 * @return a hashmap with the key being the name and value being the value.
	 */
	public HashMap<String, String> getAllEntries() {
		return entries;
	}

	/**
	 * Check whether a field is required.
	 * 
	 * @param fieldName
	 *            the name of the field.
	 * @return true if the field is required.
	 */
	public boolean isRequiredField(String fieldName) {
		return requiredEntries.contains(fieldName);
	}

	/**
	 * Check whether a field is a required Or field.
	 * 
	 * @param fieldName
	 *            the name of the field
	 * @return if it is, a HashSet containing the other required Or fields.
	 *         Otherwise null.
	 */
	public HashSet<String> isRequiredOrField(String fieldName) {
		for (int i = 0; i < requiredOrEntries.size(); i++) {
			HashSet<String> set = requiredOrEntries.get(i);
			if (set.contains(fieldName))
				return set;
		}
		return null;
	}

	/**
	 * Equivalent to clone().
	 * 
	 * @return a new citation instance containing the same values.
	 */
	public Citation copy() {
		Citation c = CitationFactory.createCitation(getType(), getKey());
		for (Entry<String, String> set : entries.entrySet()) {
			c.set(set.getKey(), set.getValue());
		}
		return c;
	}

	/**
	 * Validate the format of the citation type. The correct format should be a
	 * string consists of one or more alpha-numeric characters ignoring case.
	 * 
	 * @param s
	 *            the type to be validated.
	 * @throws InputFormatException
	 */
	public static void validateType(String s) throws InputFormatException {
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				throw new InputFormatException(
						"Type "
								+ s
								+ " contains characters other than letters and digits.\n"
								+ c + " is not allowed.");
			}
		}
	}

	/**
	 * Validate the format of the key. The correct format should be a string
	 * consisting of one or more alpha-numeric characters and any of the
	 * following characters: +_:-/.
	 * 
	 * @param s
	 *            the key to be checked.
	 * @throws InputFormatException
	 */
	public static void validateKey(String s) throws InputFormatException {
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)
					&& !SPECIAL_CHARACTERS.contains(c + "")) {
				throw new InputFormatException(
						"Key "
								+ s
								+ " contains characters other than letters, digits and special characters "
								+ SPECIAL_CHARACTERS + ".\n" + c
								+ " is not allowed.");
			}
		}
	}

	/**
	 * Validate the format of the value. A value can consist of any number
	 * (including zero) of characters including spaces, letters, digits, and any
	 * of +_:-/.
	 * 
	 * @param s
	 *            the value to be checked.
	 * @throws InputFormatException
	 */
	public static void validateFieldValue(String s) throws InputFormatException {
		// if(s.trim().equals(""))
		// throw new InputFormatException("Empty field value is not allowed.");
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)
					&& !SPECIAL_CHARACTERS.contains(c + "") && c != ' ') {
				throw new InputFormatException(
						"Value "
								+ s
								+ " contains characters other than space, letters, digits and special characters "
								+ SPECIAL_CHARACTERS + ".\n" + c
								+ " is not allowed.");
			}
		}
	}

	/**
	 * Validate the format of field names. A validate field name cannot be a
	 * empty string.
	 * 
	 * @param s
	 *            the field name to be checked.
	 * @throws InputFormatException
	 */
	public static void validateFieldName(String s) throws InputFormatException {
		if (s.trim().equals(""))
			throw new InputFormatException("Empty field name is not allowed.");
	}

	/**
	 * Validate the format of the value. Does not allow spaces.
	 * 
	 * @param s
	 *            the value to be checked.
	 * @throws InputFormatException
	 */
	public static void validateValueNoSpace(String s)
			throws InputFormatException {
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)
					&& !SPECIAL_CHARACTERS.contains(c + "")) {
				throw new InputFormatException(
						"Value "
								+ s
								+ " contains characters other than letters, digits and special characters "
								+ SPECIAL_CHARACTERS + ".\n" + c
								+ " is not allowed.");
			}
		}
	}

	/**
	 * Check whether the type, value and key of the citation is valid. Only
	 * checks the string value. To check whether a citation has all required
	 * fields, use hasRequiedFields().
	 * 
	 * @param c
	 *            the citation to be checked.
	 * @throws InputFormatException
	 */
	public static void validateCitation(Citation c) throws InputFormatException {
		validateKey(c.getKey());
		validateType(c.getType());
		for (Entry<String, String> e : c.entries.entrySet()) {
			validateFieldName(e.getKey());
			validateFieldValue(e.getValue());
		}
	}
}
