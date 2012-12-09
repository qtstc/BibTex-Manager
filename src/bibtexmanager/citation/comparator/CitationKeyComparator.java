package bibtexmanager.citation.comparator;

import java.util.Comparator;

import bibtexmanager.citation.citationtype.Citation;

/**
 * The comparator class that compares two instances of citations by their keys.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public class CitationKeyComparator implements Comparator<Citation> {

	@Override
	public int compare(Citation o1, Citation o2) {
		return o1.getKey().compareTo(o2.getKey());
	}

}
