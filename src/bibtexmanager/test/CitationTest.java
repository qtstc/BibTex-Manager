package bibtexmanager.test;

import static org.junit.Assert.*;
import org.junit.Test;

import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.citationtype.*;

/**
 * This class test the citation classes.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class CitationTest {

	@Test
	public void articleTest() {
		// Create a valid article instance
		Article article = new Article("articlekey");
		article.set(EntryName.TITLE, "t");
		article.set(EntryName.AUTHOR, "a");
		article.set(EntryName.JOURNAL, "j");
		assertFalse(article.hasRequiredFields());// Do not have all fields yet.
		article.set(EntryName.YEAR, "y");
		assertTrue(article.hasRequiredFields());// Not have all the fields.
	}

	@Test
	public void bookTest() {
		// Create a valid book instance
		Book citation = new Book("key");
		citation.set(EntryName.PUBLISHER, "p");
		citation.set(EntryName.TITLE, "t");
		citation.set(EntryName.YEAR, "y");
		assertFalse(citation.hasRequiredFields());// Do not have all fields yet.
		citation.set(EntryName.AUTHOR, "a");// Either author or editor is
											// required.
		assertTrue(citation.hasRequiredFields());// Not have all the fields.
	}

	@Test
	public void inbookTest() {
		// Create a valid article instance
		Inbook citation = new Inbook("key");
		citation.set(EntryName.PUBLISHER, "p");
		citation.set(EntryName.TITLE, "t");
		citation.set(EntryName.YEAR, "y");
		assertFalse(citation.hasRequiredFields());// Do not have all fields yet.
		citation.set(EntryName.AUTHOR, "a");// Either author or editor is
											// required.
		citation.set(EntryName.PAGES, "p");// Either page or chapter.
		assertTrue(citation.hasRequiredFields());// Not have all the fields.
	}

	@Test
	public void incollectionTest() {
		// Create a valid incollection instance
		Incollection citation = new Incollection("key");
		citation.set(EntryName.BOOKTITLE, "b");
		citation.set(EntryName.TITLE, "t");
		citation.set(EntryName.YEAR, "y");
		assertFalse(citation.hasRequiredFields());// Do not have all fields yet.
		citation.set(EntryName.AUTHOR, "a");
		assertTrue(citation.hasRequiredFields());// Not have all the fields.
	}

	@Test
	public void inproceedingsTest() {
		// Create a valid inproceedings instance
		Inproceedings citation = new Inproceedings("key");
		citation.set(EntryName.BOOKTITLE, "b");
		citation.set(EntryName.TITLE, "t");
		citation.set(EntryName.YEAR, "y");
		citation.set(EntryName.PUBLISHER, "s");
		assertFalse(citation.hasRequiredFields());// Do not have all fields yet.
		citation.set(EntryName.AUTHOR, "a");
		assertTrue(citation.hasRequiredFields());// Not have all the fields.
	}

	@Test
	public void phdthesisTest() {
		// Create a valid phdthesis instance
		Phdthesis citation = new Phdthesis("key");
		citation.set(EntryName.SCHOOL, "b");
		citation.set(EntryName.TITLE, "t");
		citation.set(EntryName.YEAR, "y");
		assertFalse(citation.hasRequiredFields());// Do not have all fields yet.
		citation.set(EntryName.AUTHOR, "a");
		assertTrue(citation.hasRequiredFields());// Not have all the fields.
	}

	@Test
	public void miscTest() {
		// Create a misc instance
		Misc citation = new Misc("key");
		assertTrue(citation.hasRequiredFields());// Misc has no required fields
	}

}
