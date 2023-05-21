package chess;

/**
 * Gets the idea form Forestf90's GameFrame.java
 * Rewrited the whole class
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chess.board.Board;

public class GameFrame extends JFrame
{
	private MoveHistory movehistory = new MoveHistory();
	private InGameControl ingamec;
	
	private JPanel eastPanel;
	
	/**
	 * This creates the new window for the game board, and to it's controls and another scrollpane to show the movehistory
	 * @param boardPanel this is the caller for the Board
	 * @param mode is for the saving and loading methods, this contains the type of the game, pvp or pvm
	 * @param mh is for the saving and loading methods, this contains the move history
	 */
	public GameFrame(Board boardPanel, String mode, String mh)
	{
		/**
		 * Here I rename the frame and reset the icon of the frame
		 */
		super("Chess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/icon.png"));
		this.setIconImage(icon.getImage());
		
		/**
		 * setting the move history with the given steps as a string, and steps count as an int
		 */
		movehistory.setMoveHistory(mh);
		
		/**
		 * initialize the right side of the frame
		 */
		initializeEastPanel(mode);
		
		/**
		 * sets the sides of the frame
		 */
		this.add(boardPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		
		/**
		 * sets the viewing parameters of the frame
		 */
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * This creates the right side of the Frame
	 * It calls 2 other classes
	 * Initialize the control panel for the game 
	 * @param mode is for the in game control classes saving call
	 */
	public void initializeEastPanel(String mode)
	{
		/**
		 * here calls for the InGameControl constructor
		 */
		ingamec = new InGameControl(mode);
		
		/**
		 * calls the JPanel constructor and sets a grid of 2 rows and 1 columns
		 * then adds the 2 pieces to the panel
		 */
		eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(2, 1));
		eastPanel.add(ingamec);
		eastPanel.add(movehistory);
	}	
}