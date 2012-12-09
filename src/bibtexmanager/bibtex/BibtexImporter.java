package bibtexmanager.bibtex;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

import bibtexmanager.citation.CitationFactory;
import bibtexmanager.citation.citationtype.*;

/**
 * This class reads in Bibtex formatted data and parses out Citation instances.
 * It has the ability to read a file's raw data line by line and sort the
 * information based on key, author, title, note, and year. Then, the new
 * information is converted into a Citation instance that stores the data in an
 * ordered fashion. This process can be performed on a file that contains the
 * information of multiple citations.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsha <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class BibtexImporter implements CitationImporter {

	// The scanner used to read input.
	private Scanner in;
	private String nextLineCache;

	/**
	 * creates an importer which reads bibtex formatted data from the specified
	 * {@link java.io.Reader}
	 * 
	 * @param input
	 *            the reader from which bibtex formatted data is read.
	 */
	public BibtexImporter(Reader input) {
		in = new Scanner(input);
		nextLineCache = "";
	}

	/**
	 * reads in and parses the next bibtex reference. If the reader is closed or
	 * no additional reference exists, null is returned.
	 * 
	 * @return the next Citation read from the datasource
	 */
	public Citation getNextCitation() {
		String line = nextLineCache;
		String newKey = "";
		String newType = "";

		if (!line.contains("{") || !line.contains("@")) {
			throw new InputFormatException(
					"First line contains neither \\{ nor @");
		}
		String[] tokens = line.split("\\{");
		newType = tokens[0].trim();
		newType = newType.substring(1, newType.length()).trim();// Remove the @
		// in front.
		newKey = tokens[1].trim();
		newKey = newKey.substring(0, newKey.length() - 1).trim();// Get rid of
		// the
		// common
		// behind.
		Citation.validateType(newType);
		Citation.validateKey(newKey);

		newType = newType.toLowerCase();// Convert the type to lowercase.

		Citation c = CitationFactory.createCitation(newType, newKey);

		// Read in all fields and add them to the citation.
		while (true) {
			line = in.nextLine();
			// If last line, which only contains a "}" or nothing.
			if (line.trim().length() <= 1)
				break;
			String[] entries = line.split("=");
			if (entries.length != 2) {
				throw new InputFormatException(
						"Entry does not contain equal sign.");
			}
			String fieldName = entries[0].trim().toLowerCase();
			Citation.validateFieldName(fieldName);
			String fieldValue = entries[1].trim();
			if (fieldValue.endsWith(","))
				fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
			fieldValue = parseValue(fieldValue);// Parse value.
			c.set(fieldName, fieldValue);
		}

		if (!c.hasRequiredFields())
			throw new InputFormatException("Citation " + c.getKey()
					+ " of type " + c.getType() + " is invalid \n"
					+ "because a required field is missing.");

		return c;
	}

	/**
	 * Validate and parse the value. A value can consist of any number
	 * (including zero) of characters including spaces, letters, digits, and any
	 * of +_:-/. surrounded by a pair of {}'s or ""'s, however values consisting
	 * only of alpha-numeric characters do not need surrounding {}'s or ""'s.
	 * 
	 * @param s
	 * @return the parsed value with "{}" and """removed.
	 * @throws InputFormatException
	 */
	private String parseValue(String s) throws InputFormatException {
		s = s.trim();
		if (s.startsWith("{") ^ s.endsWith("}")) {
			throw new InputFormatException(
					"Incomplete bracket surrounding the value.");
		}
		if (s.startsWith("\"") ^ s.endsWith("\"")) {
			throw new InputFormatException(
					"Incomplete quote surrounding the value.");
		}
		if (s.startsWith("{") || s.startsWith("\"")) {
			s = s.substring(1, s.length() - 1).trim();
			Citation.validateFieldValue(s);
		} else {
			Citation.validateValueNoSpace(s);
		}
		return s;
	}

	/**
	 * determines whether or not there are additional bibtex references to be
	 * read from the datasource.
	 * 
	 * @return wheteher or not there are additional bibtex references to be read
	 */
	public boolean isFinishedReading() {
		while (true) {
			if (in.hasNextLine()) {
				nextLineCache = in.nextLine();
				if (!nextLineCache.trim().equals(""))
					return false;
			} else
				return true;
		}
	}

	/**
	 * reads all citations avaialble from the datasource and returns them as an
	 * {@link java.util.ArrayList}.
	 * 
	 * @return all remaining citations available from the datasource
	 */
	public ArrayList<Citation> readAllRemainingCitations() {
		ArrayList<Citation> list = new ArrayList<Citation>();
		while (!isFinishedReading()) {
			Citation temp = getNextCitation();
			if (temp == null) {
				break;
			}
			list.add(temp);
		}
		return list;
	}

}
