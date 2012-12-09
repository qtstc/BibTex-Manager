package bibtexmanager.citation.comparator;

import bibtexmanager.citation.InvalidCitationException;
import bibtexmanager.citation.citationtype.Citation;

/**
 * A class used to store a static method used to check whether two citations are
 * comparable. This class maybe renamed and used to store other static method in
 * the future.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class CitationComparator {

	/**
	 * Check whether two citations are comparable. Two citations are comparable
	 * when both of them contains the given field. A exception will be thrown if
	 * they are not comparable.
	 * 
	 * @param c0
	 *            the first citation to be checked.
	 * @param c1
	 *            the second citation to be checked.
	 * @param fieldName
	 *            the name of the field.
	 * @throws InvalidCitationException
	 *             an exception indicating the two citation are not comparable.
	 */
	public static void isComparable(Citation c0, Citation c1, String fieldName)
			throws InvalidCitationException {
		String v0 = c0.get(fieldName);
		String v1 = c1.get(fieldName);

		if (v0.equals(Citation.EMPTY_ENTRY) || v1.equals(Citation.EMPTY_ENTRY))
			throw new InvalidCitationException("Citation " + c0.getKey()
					+ " of type " + c0.getType() + " is not comparable with \n"
					+ "Citation " + c1.getKey() + " of type " + c1.getType()
					+ " because the field " + fieldName
					+ " is not present in both citations.");
	}
}
