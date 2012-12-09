package bibtexmanager.citation;

/**
 * This is an exception class that is used to notify the user the format of the
 * given citation is wrong.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
@SuppressWarnings("serial")
public class InvalidCitationException extends RuntimeException {
	/**
	 * Create a new invalid citation exception. Invalid citation exception is
	 * used to notify the user that the citation is invalid. Reasons for a
	 * citation to be invalid include not containing required fields.
	 * 
	 * @param message
	 *            the message to be included.
	 */
	public InvalidCitationException(String message) {
		super(message);
	}

	public String toString() {
		return getMessage();
	}
}