package bibtexmanager.citation.comparator;

import java.util.Comparator;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.citationtype.Citation;

/**
 * The comparator class that compares two instances of citations by their years.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public class CitationYearComparator implements Comparator<Citation> {

	@Override
	public int compare(Citation o1, Citation o2) {
		CitationComparator.isComparable(o1, o2, EntryName.YEAR);
		return o1.get(EntryName.YEAR).compareTo(o2.get(EntryName.YEAR));
	}

}
