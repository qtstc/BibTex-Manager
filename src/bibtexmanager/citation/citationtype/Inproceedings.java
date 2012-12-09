package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.TypeName;

/**
 * This citation class stores an article in a conference proceedings.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Inproceedings extends Citation {

	/**
	 * Construct a new citation instance of type key.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Inproceedings(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
		addRequiredEntries(EntryName.TITLE, EntryName.BOOKTITLE,
				EntryName.AUTHOR, EntryName.PUBLISHER, EntryName.YEAR);
	}

	@Override
	protected void setType() {
		type = TypeName.INPROCEEDINGS;
	}

}
