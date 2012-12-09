package bibtexmanager.test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

//NOTE: replace the following with your import statements
import bibtexmanager.bibtex.BibtexImporter;
import bibtexmanager.bibtex.CitationImporter;
import bibtexmanager.bibtex.InputFormatException;
import bibtexmanager.citation.InvalidCitationException;
import bibtexmanager.citation.citationtype.Citation;
import bibtexmanager.citation.comparator.*;

/**
 * This class tests the citation importer by parsing a text containing
 * citations.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */

public class ImporterTestDriver {

	@Test
	public void testOnlineDoc() {
		URL sampleFile;
		try {
			sampleFile = new URL(
					"http://www.csc.depauw.edu/~jmorwick/csc232/ai.bib");
			CitationImporter importer = new BibtexImporter(
					new InputStreamReader(sampleFile.openStream()));

			int citationsRead = 1;
			ArrayList<Citation> list = new ArrayList<Citation>();
			while (!importer.isFinishedReading()) {
				Citation c = importer.getNextCitation();
				list.add(c);
				System.err.println("#" + citationsRead
						+ " - PRINTING BIBTEX...");
				System.err.println(c); // print the citation
				System.err.println("\n"); // print two blank lines
				citationsRead++;
			}
			System.out.println("\n\n **********SORTED CITATIONS: ");
			citationsRead = 1;
			Collections.sort(list, new CitationYearComparator()); // NOTE: use
			// your
			// comparators
			// here to
			// test
			for (Citation c : list) {
				System.err.println("#" + citationsRead
						+ " - PRINTING BIBTEX...");
				System.err.println(c); // print the citation
				System.err.println("\n"); // print two blank lines
				citationsRead++;
			}
			System.err.println("DONE!");
			assertTrue(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidCitationException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void readValidEntry() {
		System.out.println("Citation text for a valid citation:");
		String testString = "@article{herlocker2004,\njournal = \"ACM Trans. Inf. Syst.\",\nauthor = \"Herlocker Jonathan  L.  and Konstan Joseph  A.  and Terveen Loren  G.  and Riedl John  T. \",\ntitle = {Evaluating collaborative filtering recommender systems},\naddress = {New York NY USA},\npages = {5-53},\nnumber = 1,\nyear = {2004},\npublisher = \"ACM Press\"\n}";
		System.out.println(testString);
		StringReader r = new StringReader(testString);
		CitationImporter importer = new BibtexImporter(r);
		if (!importer.isFinishedReading()) {
			System.out.println("The citation read by importer:");
			System.out.println(importer.getNextCitation().toStringDetailed());
		}
	}

	@Test
	public void readOldEntry() {
		try {
			// A test book citation text without author field.
			String testString = "@article{herlocker2004,\njournal = \"ACM Trans. Inf. Syst.\",\ntitle = {Evaluating collaborative filtering recommender systems},\naddress = {New York NY USA},\npages = {5-53},\nnumber = 1,\nyear = {2004},\npublisher = \"ACM Press\"\n}";
			System.out.println(testString);
			StringReader r = new StringReader(testString);
			CitationImporter importer = new BibtexImporter(r);
			if (!importer.isFinishedReading())
				System.out.println(importer.getNextCitation()
						.toStringDetailed());
		} catch (InputFormatException e) {
			e.printStackTrace();
			assertTrue(true);
		}
	}

}