package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.TypeName;

/**
 * This citation class stores book with an explicit publisher.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Book extends Citation {

	/**
	 * Construct a new citation instance of type book.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Book(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
		addRequiredEntries(EntryName.TITLE, EntryName.PUBLISHER, EntryName.YEAR);
		addRequiredOrEntries(EntryName.AUTHOR, EntryName.EDITOR);
	}

	@Override
	protected void setType() {
		// TODO Auto-generated method stub
		type = TypeName.BOOK;
	}
}
