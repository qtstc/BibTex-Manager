package bibtexmanager.citation;

import bibtexmanager.citation.citationtype.Article;
import bibtexmanager.citation.citationtype.Book;
import bibtexmanager.citation.citationtype.Citation;
import bibtexmanager.citation.citationtype.Inbook;
import bibtexmanager.citation.citationtype.Incollection;
import bibtexmanager.citation.citationtype.Inproceedings;
import bibtexmanager.citation.citationtype.Misc;
import bibtexmanager.citation.citationtype.Phdthesis;

/**
 * A citation factory used to create citation instances.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsha <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public class CitationFactory {

	public final static String DEFAULT_KEY = "new_key";// Key used for default
														// citations.

	/**
	 * Create citation instances depending on type and key.
	 * 
	 * @param newType
	 *            type of the citation
	 * @param newKey
	 *            key of the citation
	 * @return the citation created.
	 */
	public static Citation createCitation(String newType, String newKey) {
		Citation c;
		if (newType.equals(TypeName.ARTICLE))
			c = new Article(newKey);
		else if (newType.equals(TypeName.BOOK))
			c = new Book(newKey);
		else if (newType.equals(TypeName.INBOOK))
			c = new Inbook(newKey);
		else if (newType.equals(TypeName.INCOLLECTION))
			c = new Incollection(newKey);
		else if (newType.equals(TypeName.INPROCEEDINGS))
			c = new Inproceedings(newKey);
		else if (newType.equals(TypeName.PHDTHESIS))
			c = new Phdthesis(newKey);
		else
			// if(newType.equals(TypeName.MISC))
			c = new Misc(newKey);// Used Misc for all other types.
		// else throw new
		// InputFormatException(newType+" is not a valid citation format");
		return c;
	}

	/**
	 * Return all possible types of ciations
	 * 
	 * @return all possible types of citations
	 */
	public static String[] getCitationTypes() {
		String[] types = new String[7];
		types[0] = TypeName.ARTICLE;
		types[1] = TypeName.BOOK;
		types[2] = TypeName.INBOOK;
		types[3] = TypeName.INCOLLECTION;
		types[4] = TypeName.INPROCEEDINGS;
		types[5] = TypeName.PHDTHESIS;
		types[6] = TypeName.MISC;
		return types;
	}

	/**
	 * Create a default citation. It has the default key, misc type and no other
	 * fields.
	 * 
	 * @return the new Citation
	 */
	public static Citation getDefaultCitation() {
		return createCitation(TypeName.MISC, DEFAULT_KEY);
	}
}
