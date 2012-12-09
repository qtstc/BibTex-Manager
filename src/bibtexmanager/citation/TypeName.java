package bibtexmanager.citation;

/**
 * This class is used as a repository for different citation type names. All
 * citation types stored in this class are in lower cases.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public final class TypeName {

	private TypeName() {
	};

	public final static String ARTICLE = "article";
	public final static String BOOK = "book";
	public final static String INBOOK = "inbook";
	public final static String INCOLLECTION = "incollection";
	public final static String INPROCEEDINGS = "inproceedings";
	public final static String PHDTHESIS = "phdthesis";
	public final static String MISC = "misc";
}
