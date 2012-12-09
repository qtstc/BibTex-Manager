package bibtexmanager.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.junit.Test;

import bibtexmanager.bibtex.Bibliography;
import bibtexmanager.bibtex.BibtexImporter;
import bibtexmanager.bibtex.CitationImporter;
import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.citationtype.Citation;
import bibtexmanager.citation.comparator.CitationKeyComparator;

/**
 * This class tests the function of the bibliography class.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public class BibliographyTest {

	@Test
	public void testBibInsert() throws IOException {
		URL sampleFile = new URL(
				"http://www.csc.depauw.edu/~jmorwick/csc232/ai.bib");
		CitationImporter importer = new BibtexImporter(new InputStreamReader(
				sampleFile.openStream()));
		Bibliography bib = new Bibliography(importer);
		// Check original size
		assertEquals(bib.size(), 136);

		// Check getAllCitations()
		assertEquals(bib.getAllCitations().size(), 136);

		// Check removeCitation()
		bib.removeCitation(bib.getAllCitations().get(0));
		assertEquals(bib.size(), 135);

		// Check searchBy()
		ArrayList<Citation> searchResult = bib.searchBy(EntryName.KEY, "ab");
		for (Citation c : searchResult)
			assertTrue(c.getKey().contains("ab"));

		// Check comparator(sorting)
		bib.setComparator(new CitationKeyComparator());
		ArrayList<Citation> sortedCitation = bib.getAllCitations();
		for (int i = 0; i < sortedCitation.size() - 1; i++) {
			assertTrue(sortedCitation.get(i).getKey()
					.compareTo(sortedCitation.get(i + 1).getKey()) < 0);
		}
	}
}
