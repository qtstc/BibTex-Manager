package bibtexmanager.citation;

/**
 * This class is used as a repository for different citation entry names. All
 * citation entry names stored in this class are in lower cases.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 */
public final class EntryName {

	private EntryName() {
	};

	public final static String KEY = "key";
	public final static String TYPE = "type";
	public final static String TITLE = "title";
	public final static String YEAR = "year";
	public final static String AUTHOR = "author";
	public final static String EDITOR = "editor";
	public final static String ADDRESS = "address";
	public final static String PUBLISHER = "publisher";
	public final static String JOURNAL = "journal";
	public final static String CHAPTER = "chapter";
	public final static String PAGES = "pages";
	public final static String BOOKTITLE = "booktitle";
	public final static String SCHOOL = "school";
	public final static String ANNOTATION = "annotation";

	public final static String MONTH = "month";
	public final static String EDITION = "edition";

	public static String[] getAllEntryNames() {
		String[] names = new String[16];
		names[0] = KEY;
		names[1] = TYPE;
		names[2] = TITLE;
		names[3] = YEAR;
		names[4] = AUTHOR;
		names[5] = EDITOR;
		names[6] = ADDRESS;
		names[7] = PUBLISHER;
		names[8] = JOURNAL;
		names[9] = CHAPTER;
		names[10] = PAGES;
		names[11] = BOOKTITLE;
		names[12] = SCHOOL;
		names[13] = ANNOTATION;
		names[14] = MONTH;
		names[15] = EDITION;
		return names;
	}
}
