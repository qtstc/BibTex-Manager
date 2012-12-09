package bibtexmanager.bibtex;

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
public class InputFormatException extends RuntimeException {
	/**
	 * Create an input format exception. An input format exception is used to
	 * notify the user that the format of the input is wrong.
	 * 
	 * @param message
	 *            the message to be included
	 */
	public InputFormatException(String message) {
		super(message);
	}

	public String toString() {
		return getMessage();
	}
}
