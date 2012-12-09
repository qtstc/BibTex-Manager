package bibtexmanager.citation.comparator;

import java.util.Comparator;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.citationtype.Citation;

/**
 * The comparator class that compares two instances of citations by their
 * publishers.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public class CitationPublisherComparator implements Comparator<Citation> {

	@Override
	public int compare(Citation o1, Citation o2) {
		CitationComparator.isComparable(o1, o2, EntryName.PUBLISHER);
		return o1.get(EntryName.PUBLISHER).compareTo(
				o2.get(EntryName.PUBLISHER));
	}
}
