package bibtexmanager.bibtex;

import java.util.ArrayList;

import bibtexmanager.citation.citationtype.Citation;

/**
 * This is an interface that supports parsing citations from text.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public interface CitationImporter {
	// The scanner used to read input.

	/**
	 * reads in and parses the next bibtex reference. If the reader is closed or
	 * no additional reference exists, null is returned.
	 * 
	 * @return the next Citation read from the datasource
	 */
	public Citation getNextCitation();

	/**
	 * determines whether or not there are additional bibtex references to be
	 * read from the datasource.
	 * 
	 * @return wheteher or not there are additional bibtex references to be read
	 */
	public boolean isFinishedReading();

	/**
	 * reads all citations avaialble from the datasource and returns them as an
	 * {@link java.util.ArrayList}.
	 * 
	 * @return all remaining citations available from the datasource
	 */
	public ArrayList<Citation> readAllRemainingCitations();
}
