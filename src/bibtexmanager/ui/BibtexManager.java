package bibtexmanager.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import bibtexmanager.bibtex.Bibliography;

/**
 * The main class for the application.
 * 
 * @author Troy Holleman <troyholleman_2015@depauw.edu>
 * @author Tao Qian <taoqian_2015@depauw.edu>
 * @author Ben Harsh <benjaminharsha_2015@depauw.edu>
 * @author JD Hoover <johnhoover_2014@depauw.edu>
 * 
 */
public class BibtexManager {

	public final static String APP_NAME = "Bibtex Manager";

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame(APP_NAME);
		MainPanel mainPanel = new MainPanel(new Bibliography());
		mainFrame.setContentPane(mainPanel);
		mainFrame.setJMenuBar(mainPanel.getMenuBar());
		mainFrame.setVisible(true);
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		//Set the icon of the image
        BufferedImage image = null;
        try {
            image = ImageIO.read(
            		new File("res/icon.png"));
        } catch (IOException e) {
            image = null;
        }
        mainFrame.setIconImage(image);
	}
}
