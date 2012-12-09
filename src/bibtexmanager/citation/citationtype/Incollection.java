package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.TypeName;

/**
 * This citation class stores a part of a book with its own title.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Incollection extends Citation {

	/**
	 * Construct a new citation instance of type incollection.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Incollection(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
		addRequiredEntries(EntryName.TITLE, EntryName.YEAR,
				EntryName.BOOKTITLE, EntryName.AUTHOR);
	}

	@Override
	protected void setType() {
		type = TypeName.INCOLLECTION;
	}

}
