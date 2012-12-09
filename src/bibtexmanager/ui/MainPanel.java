package bibtexmanager.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bibtexmanager.bibtex.Bibliography;
import bibtexmanager.bibtex.BibtexImporter;
import bibtexmanager.bibtex.InputFormatException;
import bibtexmanager.citation.CitationFactory;
import bibtexmanager.citation.EntryName;
import bibtexmanager.citation.InvalidCitationException;
import bibtexmanager.citation.citationtype.Citation;
import bibtexmanager.citation.comparator.CitationAuthorComparator;
import bibtexmanager.citation.comparator.CitationKeyComparator;
import bibtexmanager.citation.comparator.CitationPublisherComparator;
import bibtexmanager.citation.comparator.CitationTitleComparator;
import bibtexmanager.citation.comparator.CitationYearComparator;

/**
 * This is the main panel of the BibtexManager application. It contains a list
 * of citations, a search panel and a table for edit citation.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	private final JFileChooser fc = new JFileChooser();
	private Bibliography bib;// The bibliography file currently used by the
								// panel.
	private Citation[] selected;// The citations that are selected.
	private CitationEditPanel editPanel;
	private CitationSearchPanel searchPanel;
	private CitationListPanel listPanel;
	private int textFieldWidth = 10;

	public MainPanel(Bibliography bib) {
		// Split the main panel into two, with the left being the citation list,
		// right being text area.
		JSplitPane MainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		editPanel = new CitationEditPanel(null);
		searchPanel = new CitationSearchPanel();
		listPanel = new CitationListPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("List", new JScrollPane(listPanel));
		tabbedPane.add("Search", searchPanel);
		setBibliography(bib);
		MainSplitPane.setLeftComponent(tabbedPane);
		MainSplitPane.setRightComponent(editPanel);
		add(MainSplitPane);
	}

	/**
	 * Change the bibliography of this JPanel. Can be called externally.
	 * 
	 * @param the
	 *            new bib file.
	 */
	private void setBibliography(Bibliography bib) {
		this.bib = bib;
		listPanel.updateListData();
		searchPanel.updateSearchListData();
		editPanel.setCitation(null);
		selected = null;// Clear selected citations
	}

	/**
	 * Construct and return the menu bar
	 * 
	 * @return the menu bar constructed.
	 */
	public JMenuBar getMenuBar() {
		// Open file menu item
		JMenuItem fileOpenItem = new JMenuItem("Open...");
		fileOpenItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(MainPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					try {
						Bibliography temp = new Bibliography(
								new BibtexImporter(new FileReader(file)));
						setBibliography(temp);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(MainPanel.this,
								"Failed to open the file.");
					} catch (InputFormatException e) {
						JOptionPane.showMessageDialog(MainPanel.this,
								"Wrong citation format:\n" + e.toString());
					}
				}
			}
		});
		// Save file menu item.
		JMenuItem fileSaveItem = new JMenuItem("Save");
		fileSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showSaveDialog(MainPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					FileWriter writer = null;
					try {
						writer = new FileWriter(file);
						if (bib != null)
							writer.write(bib.toString());
						writer.flush();
						writer.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainPanel.this,
								"Failed to save the file.");
					}
				}
			}
		});
		// File menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(fileOpenItem);
		fileMenu.add(fileSaveItem);
		// Remove citation menu item
		JMenuItem removeCitationItem = new JMenuItem("Remove citation");
		removeCitationItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selected == null) {
					JOptionPane.showMessageDialog(MainPanel.this,
							"Please selected at least one citation");
					return;
				}
				for (int i = 0; i < selected.length; i++) {
					bib.removeCitation(selected[i]);
				}
				listPanel.updateListData();
				searchPanel.updateSearchListData();
			}
		});
		// Add citation menu item
		JMenuItem addCitationItem = new JMenuItem("Add citation");
		addCitationItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.updateListData();
				searchPanel.updateSearchListData();
				editPanel.setCitation(null);
				editPanel.keyField.requestFocus();
				editPanel.keyField.selectAll();
			}
		});

		// Sort by author menu item
		JMenuItem sortByAuthorItem = new JMenuItem("By Author");
		sortByAuthorItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sort(new CitationAuthorComparator());
			}
		});
		// Sort by key menu item
		JMenuItem sortByKeyItem = new JMenuItem("By Key");
		sortByKeyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sort(new CitationKeyComparator());
			}
		});
		// Sort by title menu item
		JMenuItem sortByTitleItem = new JMenuItem("By Title");
		sortByTitleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sort(new CitationTitleComparator());
			}
		});
		// Sort by Year menu item
		JMenuItem sortByYearItem = new JMenuItem("By Year");
		sortByYearItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sort(new CitationYearComparator());
			}
		});
		// Sort by Publisher menu item
		JMenuItem sortByPublisherItem = new JMenuItem("By Publisher");
		sortByPublisherItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sort(new CitationPublisherComparator());
			}
		});
		// Sort menu
		JMenu sortMenu = new JMenu("Sort");
		sortMenu.add(sortByKeyItem);
		sortMenu.add(sortByTitleItem);
		sortMenu.add(sortByAuthorItem);
		sortMenu.add(sortByYearItem);
		sortMenu.add(sortByPublisherItem);

		// Bibliography menu
		JMenu bibMenu = new JMenu("Bibliography");
		bibMenu.add(removeCitationItem);
		bibMenu.add(addCitationItem);
		bibMenu.add(sortMenu);
		// Main menu bar.
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(bibMenu);
		return menuBar;
	}

	/**
	 * This method sort the bibliography using the comparator passed to it.
	 * 
	 * @param comp
	 *            the comparator used
	 */
	private void sort(Comparator<Citation> comp) {
		if (bib == null)
			return;
		bib.setComparator(comp);
		try {
			listPanel.updateListData();
		} catch (InvalidCitationException e) {
			JOptionPane.showMessageDialog(MainPanel.this, e.toString());
			bib.setComparator(null);
			listPanel.updateListData();
		}
	}

	/**
	 * This class is the GUI that shows a list of citations of the bibliography.
	 * It is made an inner class because the main panel needs to use its
	 * variable frequently.
	 * 
	 * @author Troy Holleman <troyholleman_2015@depauw.edu>
	 * @author Tao Qian <taoqian_2015@depauw.edu>
	 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
	 * @author JD Hoover <johnhoover_2014@depauw.edu>
	 * 
	 */
	private class CitationListPanel extends JList {

		/**
		 * Constructor.
		 */
		private CitationListPanel() {
			super();
			// Updated the selected list every time citations are selected.
			addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent event) {
					if (!event.getValueIsAdjusting()) {
						Object[] temp = getSelectedValues();
						selected = new Citation[temp.length];
						for (int i = 0; i < selected.length; i++)
							selected[i] = (Citation) temp[i];
						if (selected.length == 1)
							editPanel.setCitation(selected[0]);
					}
				}
			});
		}

		/**
		 * Update the list data. To be called every time bib is changed.
		 */
		private void updateListData() {
			clearSelection();
			DefaultListModel listData = new DefaultListModel();
			if (bib == null)
				return;
			ArrayList<Citation> list = bib.getAllCitations();
			for (int i = 0; i < list.size(); i++)
				listData.add(i, list.get(i));
			setModel(listData);
			editPanel.setCitation(null);
		}
	}

	/**
	 * This class is the GUI responsible for the editing of a citation.
	 * 
	 * @author Troy Holleman <troyholleman_2015@depauw.edu>
	 * @author Tao Qian <taoqian_2015@depauw.edu>
	 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
	 * @author JD Hoover <johnhoover_2014@depauw.edu>
	 * 
	 */
	private class CitationEditPanel extends JPanel {

		// The citation passed to the constructor of the class,
		// used to revert the changes made by the user.
		private Citation oldCitation;
		// The citation after edited by the user.
		private Citation newCitation;
		// GUI used for displaying info like key, type, etc.
		private JPanel infoPanel;
		// GUI used for the editing of the entries of the citation.
		private JPanel entryControlPanel;
		// GUI used to show the entries.
		private CitationEditTable entryTable;
		// GUI used to allow user to save or reset edited citation.
		private JPanel confirmPanel;
		// GUI for the selection of citation type.
		private JComboBox typeComboBox;
		// GUI for displaying texts.
		private JTextField keyField;

		private boolean notChangedByUser;

		/**
		 * Constructor.
		 * 
		 * @param oldCitation
		 *            the original citation to be shown in the panel. Use null
		 *            if are creating a new citation.
		 */
		private CitationEditPanel(Citation oldCitation) {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			entryTable = new CitationEditTable(null);
			entryTable.setFillsViewportHeight(true);
			initializeInfoPanel();
			initializeEntryControlPanel();
			initializeConfirmPanel();
			setCitation(oldCitation);
			add(infoPanel);
			add(entryControlPanel);
			add(new JScrollPane(entryTable));
			add(confirmPanel);
		}

		/**
		 * Set the citation currently displayed by the panel.
		 * 
		 * @param oldCitation
		 *            the citation to be shown in the panel. Use null if are
		 *            creating a new citation.
		 */
		private void setCitation(Citation oldCitation) {
			newCitation = CitationFactory.getDefaultCitation();
			this.oldCitation = oldCitation;
			if (oldCitation != null)// If we are to display an old citation.
			{
				newCitation = oldCitation.copy();// Use a copy because we don't
													// want to change the old
													// one.
				changeComboSelection(newCitation.getType());
				keyField.setText(newCitation.getKey());
			}
			keyField.setText(newCitation.getKey());
			changeComboSelection(newCitation.getType());
			entryTable.setCitation(newCitation);
		}

		/**
		 * Initialize the info panel, which is used to display info like key and
		 * type.
		 */
		private void initializeInfoPanel() {
			infoPanel = new JPanel();

			typeComboBox = new JComboBox(CitationFactory.getCitationTypes());
			typeComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!notChangedByUser) {
						String type = (String) typeComboBox.getSelectedItem();
						if (newCitation != null)
							newCitation = CitationFactory.createCitation(type,
									newCitation.getKey());
						else
							newCitation = CitationFactory.getDefaultCitation();
						entryTable.setCitation(newCitation);
						keyField.setText(newCitation.getKey());
					} else
						notChangedByUser = false;
				}
			});

			keyField = new JTextField(textFieldWidth);

			infoPanel.add(typeComboBox);
			infoPanel.add(new JLabel("Key:"));
			infoPanel.add(keyField);
		}

		/**
		 * Initialize the entry control panel, which is used to manipulate the
		 * entries of a citation.
		 */
		private void initializeEntryControlPanel() {
			entryControlPanel = new JPanel();
			JButton addButton = new JButton("Add Field");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					entryTable.addEntry(Citation.EMPTY_ENTRY,
							Citation.EMPTY_ENTRY);
				}
			});
			JButton removeButton = new JButton("Remove Field");
			removeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!entryTable.removeEntry())
						JOptionPane
								.showMessageDialog(
										CitationEditPanel.this,
										"Unable to remove selected field.\nPlease make sure the field to be removed is optional for this citation type.");
				}
			});

			entryControlPanel.add(addButton);
			entryControlPanel.add(removeButton);
		}

		/**
		 * Initialize the confirm panel, which is used to save the edited
		 * citation.
		 */
		private void initializeConfirmPanel() {
			confirmPanel = new JPanel();
			JButton saveButton = new JButton("Save");
			JButton resetButton = new JButton("Reset");

			saveButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Citation changedCit = null;
					// Getting new citation from GUI.
					// If the input is wrong, inform the user with a message
					// box.
					try {
						String newKey = keyField.getText().trim();
						Citation.validateKey(newKey);
						changedCit = entryTable.getCitation();
						changedCit.setKey(newKey);
					} catch (InputFormatException e) {
						JOptionPane.showMessageDialog(CitationEditPanel.this,
								e.toString());
					}
					// Don't do anything if did not get a valid citation.
					if (changedCit == null)
						return;
					boolean result = false;
					if (oldCitation == null)// If we are not editing a citation.
						result = bib.addCitation(changedCit);// Try to add a
																// citation to
																// the bib.
					else// If we are editing one.
					{
						// First remove the old one.
						bib.removeCitation(oldCitation);
						result = bib.addCitation(changedCit);
					}
					if (!result)// If saving failed.
					{
						JOptionPane
								.showMessageDialog(
										CitationEditPanel.this,
										"Failed to add "
												+ changedCit
												+ " to the bibliography.\n Please make sure that the bibliography does not contain a citation with the same key.");
						if (oldCitation != null)// If editing, put the original
												// citation back.
							bib.addCitation(oldCitation);
					} else// If successes
					{
						listPanel.updateListData();
						searchPanel.updateSearchListData();
					}
				}
			});

			resetButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setCitation(oldCitation);
				}
			});
			confirmPanel.add(saveButton);
			confirmPanel.add(resetButton);
		}

		/**
		 * Change the combo box selection without trigger the mehtods in action
		 * listener.
		 * 
		 * @param selection
		 *            the selected one
		 */
		private void changeComboSelection(String selection) {
			notChangedByUser = true;
			typeComboBox.setSelectedItem(selection);
		}
	}

	/**
	 * This is the GUI for searching the citations.
	 * 
	 * @author Troy Holleman <troyholleman_2015@depauw.edu>
	 * @author Tao Qian <taoqian_2015@depauw.edu>
	 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
	 * @author JD Hoover <johnhoover_2014@depauw.edu>
	 * 
	 */
	private class CitationSearchPanel extends JPanel {
		private JTextField searchText;
		private JComboBox fieldOptions;
		private JList searchList;

		/**
		 * Constructor
		 */
		private CitationSearchPanel() {
			JPanel upperPanel = new JPanel();
			fieldOptions = new JComboBox(EntryName.getAllEntryNames());
			searchText = new JTextField(textFieldWidth);
			searchList = new JList();

			JButton searchButton = new JButton("Search");

			upperPanel.add(fieldOptions);
			upperPanel.add(searchText);
			upperPanel.add(searchButton);

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(upperPanel);
			add(new JScrollPane(searchList));

			searchButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					updateSearchListData();
				}
			});

			searchList.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent event) {
					if (!event.getValueIsAdjusting()) {
						Object[] temp = searchList.getSelectedValues();
						selected = new Citation[temp.length];
						for (int i = 0; i < selected.length; i++)
							selected[i] = (Citation) temp[i];
						if (selected.length == 1)
							editPanel.setCitation(selected[0]);
						else if (selected.length == 0)
							editPanel.setCitation(null);
					}
				}
			});
		}

		/**
		 * Update the data in the list. To be called every time the bibliography
		 * changes.
		 */
		private void updateSearchListData() {
			searchList.clearSelection();
			DefaultListModel searchListData = new DefaultListModel();
			searchList.setModel(searchListData);
			String fieldKey = (String) fieldOptions.getSelectedItem();
			String searchString = searchText.getText().trim();
			if (searchString.equals(""))
				return;
			ArrayList<Citation> result = bib.searchBy(fieldKey, searchString);
			for (int i = 0; i < result.size(); i++)
				searchListData.add(i, result.get(i));
		}
	}
}
