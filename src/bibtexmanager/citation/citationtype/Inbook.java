package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.TypeName;

/**
 * This citation class stores a part of a book, which may be a chapter and/or a
 * range of pages.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Inbook extends Citation {

	/**
	 * Construct a new citation instance of type inbook.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Inbook(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
		addRequiredEntries(EntryName.TITLE, EntryName.PUBLISHER, EntryName.YEAR);
		addRequiredOrEntries(EntryName.AUTHOR, EntryName.EDITOR);
		addRequiredOrEntries(EntryName.PAGES, EntryName.CHAPTER);
	}

	@Override
	protected void setType() {
		type = TypeName.INBOOK;
	}

}
