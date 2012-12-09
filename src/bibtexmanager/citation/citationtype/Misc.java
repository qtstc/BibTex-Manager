package bibtexmanager.citation.citationtype;

import bibtexmanager.citation.TypeName;

/**
 * This citation class stores a citation of an uncommon type.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class Misc extends Citation {

	/**
	 * Construct a new citation instance of type misc.
	 * 
	 * @param key
	 *            the key for the citation.
	 */
	public Misc(String key) {
		super(key);
	}

	@Override
	protected void initializeRequiredEntries() {
	}

	@Override
	protected void setType() {
		type = TypeName.MISC;
	}

}
