package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.TypeName;

/**
 * This citation class stores a Ph.D. thesis.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Phdthesis extends Citation {

	/**
	 * Construct a new citation instance of type phdthesis.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Phdthesis(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
		addRequiredEntries(EntryName.TITLE, EntryName.AUTHOR, EntryName.SCHOOL,
				EntryName.YEAR);
	}

	@Override
	protected void setType() {
		type = TypeName.PHDTHESIS;
	}

}
