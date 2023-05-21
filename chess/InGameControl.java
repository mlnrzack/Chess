package chess;

/**
 * Gets the idea from Kev1nChen's chess game
 * Belongs to GameFrame.java
 * This creates the upper side panel, where the control buttons are
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import chess.board.Board;

public class InGameControl extends JPanel
{
	private JButton saveButton;
	private JButton backButton;
	private JButton exitButton;
	
	/**
	 * This is the constructor of this class
	 * Sets up 3 buttons, and also sets up 3 actionlisteners for them
	 * @param mode is the type of the gameplay, pvp or pvm, it's just giving it to the @method saveTrigger()
	 */
	public InGameControl(String mode)
	{
		/**
		 * Initialize the save button, and adds an action listener to that, when pressed calls the @method saveTrigger()
		 */
		saveButton = new JButton("Save");
		saveButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
		saveButton.setBackground(Color.decode("#285f5f"));
		saveButton.setForeground(Color.decode("#289696"));
		saveButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
		saveButton.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		        saveTrigger(mode);
		    }
		});
		/**
		 * Initialize the back button, and adds an action listener to that, when pressed calls the @method menuTrigger()
		 */
		backButton = new JButton("Back to Menu");
		backButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
		backButton.setBackground(Color.decode("#285f5f"));
		backButton.setForeground(Color.decode("#289696"));
		backButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
		backButton.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		        menuTrigger();
		    }
		});
		/**
		 * Initialize the exit button, and adds an action listener to that, when pressed closes the whole program
		 */
		exitButton = new JButton("Exit");
        exitButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
        exitButton.setBackground(Color.decode("#285f5f"));
		exitButton.setForeground(Color.decode("#289696"));
		exitButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
        exitButton.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		        System.exit(0);
		    }
		});
        
		this.setLayout(new GridLayout(3, 1));
		this.add(saveButton);
		this.add(backButton);
		this.add(exitButton);
		this.setBackground(Color.decode("#287878"));		
	}
	
	/**
	 * Sets the Saver class with the right parameters to be able to save the game standing correctly
	 * @param mode is the type of the gameplay, pvp or pvm
	 */
	public void saveTrigger(String mode)
	{
		/**
		 * Opens a SaveDialog with the working folder of the game, and sets a default filename, so the user can just "auto" save
		 * Also the user can override the filename or the file location as well
		 */
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Serializated Files", "ser");
		fileChooser.setFileFilter(filter);
		fileChooser.setSelectedFile(new File((java.time.LocalDate.now() + mode.toLowerCase() + ".ser")));
		fileChooser.setCurrentDirectory(new File("."));
		/**
		 * this stores the response from the save dialog
		 */
		int response = fileChooser.showSaveDialog(null);
		/**
		 * if you choosed the save option gets the path for that file you setted up
		 * it calls for Saver class, which saves the game,
		 * but before that the getBoard method, which gives the required informations to the Saver class
		 */
		if(response == JFileChooser.APPROVE_OPTION)
		{
			File savefile = new File(fileChooser.getSelectedFile().getAbsolutePath());
			Board.Save();
			Saver.saveGame(savefile, mode);
		}
		/**
		 * if you cancelled your saving progress
		 * it shows a message dialog which informs you about your cancelment
		 */
		else if(response == JFileChooser.CANCEL_OPTION)
			JOptionPane.showMessageDialog(null, "File save has been canceled!");
	}
	
	/**
	 * This is a back to menu button triggered effect
	 * calls the Menu class
	 * and closes the frame
	 */
	public void menuTrigger()
	{
		new Menu();
		SwingUtilities.windowForComponent(this).dispose();
	}
}
