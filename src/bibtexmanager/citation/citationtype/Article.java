package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.TypeName;

/**
 * This citation class stores an article from a journal or magazine
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Article extends Citation {

	/**
	 * Construct a new citation instance of type article.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Article(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
		addRequiredEntries(EntryName.TITLE, EntryName.AUTHOR,
				EntryName.JOURNAL, EntryName.YEAR);
	}

	@Override
	protected void setType() {
		type = TypeName.ARTICLE;
	}
}
