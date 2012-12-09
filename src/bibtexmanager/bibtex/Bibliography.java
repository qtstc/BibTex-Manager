package bibtexmanager.bibtex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.citationtype.Citation;

/**
 * This class represents a collection of citations and offers basic searching
 * and editing functionality. It allows users to manage citations through
 * adding, removing, and searching them. Users are also able to check if the
 * Bibliography contains a certain citation and then retrieve the selected
 * citation.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */

public class Bibliography {

	private HashMap<String, Citation> citations;
	private Comparator<Citation> comparator;

	/**
	 * Construct an empty bibliography
	 */
	public Bibliography() {
		citations = new HashMap<String, Citation>();
		comparator = null;
	}

	/**
	 * Construct a bibliography with an importer. Every citation read from the
	 * importer will be stored.
	 * 
	 * @param importer
	 */
	public Bibliography(CitationImporter importer) {
		this();
		ArrayList<Citation> temp = importer.readAllRemainingCitations();
		for (Citation c : temp) {
			citations.put(c.getKey(), c);
		}
	}

	/**
	 * Adds the specified citation to this bibliography. Will fail if the
	 * bibliography contains a citation with the same key.
	 * 
	 * @param citation
	 *            the citation to be added
	 * @return true if the operation is successful.
	 */
	public boolean addCitation(Citation citation) {
		if (citations.containsKey(citation.getKey()))
			return false;
		citations.put(citation.getKey(), citation);
		return true;
	}

	/**
	 * Removes the specified citation from this bibliography. If the specified
	 * citation does not exist within this bibliography, this method does
	 * nothing.
	 * 
	 * @param citation
	 *            the citation to be removed
	 */
	public void removeCitation(Citation citation) {
		citations.remove(citation.getKey());
	}

	/**
	 * Returns the citation with the specified key from this bibliography. If no
	 * citation with the specified key exists within this bibliography, null is
	 * returned.
	 * 
	 * @param key
	 *            the key (ID of the citation) to search by
	 * @return the citation matching the specified key
	 */
	public Citation getCitation(String key) {
		return citations.get(key);
	}

	/**
	 * Determines whether or not this bibliography contains the specified
	 * citation.
	 * 
	 * @param citation
	 *            the citation to search for
	 * @return whether or not the specified citation is contained in this
	 *         bibliography
	 */
	public boolean containsCitation(Citation citation) {
		if (getCitation(citation.getKey()) == null) {
			return false;
		}
		return true;
	}

	/**
	 * Determines whether or not this bibliography contains a citation with the
	 * specified key.
	 * 
	 * @param key
	 *            the key of the citation to search for
	 * @return whether or not the specified citation is contained in this
	 *         bibliography
	 */
	public boolean containsCitation(String key) {
		if (getCitation(key) == null) {
			return false;
		}
		return true;
	}

	/**
	 * Fetches every citation in this bibliography. If the comparator was called
	 * before, the returned list will be sorted.
	 * 
	 * @return every citation in this bibliography
	 */
	public ArrayList<Citation> getAllCitations() {
		ArrayList<Citation> unSorted = new ArrayList<Citation>(
				citations.values());
		if (comparator != null)
			Collections.sort(unSorted, comparator);
		return unSorted;
	}

	/**
	 * Fetches every citation in this bibliography with a field containing the
	 * specific search string.
	 * 
	 * @param fieldKey
	 *            the name of the field
	 * @param searchString
	 *            the string to be searched
	 * @return an ArrayList of all citations in this bibliography matching the
	 *         search criteria.
	 */
	public ArrayList<Citation> searchBy(String fieldKey, String searchString) {
		ArrayList<Citation> resultList = new ArrayList<Citation>();
		for (Citation c : citations.values()) {
			if (fieldKey.equals(EntryName.KEY)) {
				if (c.getKey().contains(searchString))
					resultList.add(c);
			} else if (fieldKey.equals(EntryName.TYPE)) {
				if (c.getType().contains(searchString))
					resultList.add(c);
			} else if (c.get(fieldKey).contains(searchString))
				resultList.add(c);
		}
		return resultList;
	}

	/**
	 * Fetches every citation in this bibliography for which any of its fields
	 * contains the search string
	 * 
	 * @return every citation in this bibliography matching the search criteria
	 */
	public ArrayList<Citation> search(String searchString) {
		ArrayList<Citation> resultList = new ArrayList<Citation>();
		for (Citation c : citations.values()) {
			boolean contains = false;
			for (String s : c.getAllFieldValue()) {
				if (s.contains(searchString))
					contains = true;
			}
			if (contains)
				resultList.add(c);
		}
		return resultList;
	}

	/**
	 * Set the comparator used to sort the citations inside this bibliography.
	 * 
	 * @param comparator
	 */
	public void setComparator(Comparator<Citation> comparator) {
		this.comparator = comparator;
	}

	/**
	 * 
	 * @return The number of citations in the bibliography
	 */
	public int size() {
		return citations.size();
	}

	/**
	 * Return a string representation of the bibliography.
	 */
	public String toString() {
		String result = "";
		ArrayList<Citation> list = getAllCitations();
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i).toStringDetailed() + "\n\n";
		}
		return result;
	}
}
