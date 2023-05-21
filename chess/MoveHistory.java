package chess;

/**
 * Gets the idea form Kev1nChen's MoveHistoryPanel.java
 * This shows the moves after the player moved a figure on the table
 */

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import chess.board.Board;
import chess.pieces.Chess_piece;

public class MoveHistory extends JPanel implements Serializable
{
	private static JScrollPane mHistoryScrollPane;
	private static JTextArea mHistoryTextArea;
	private static String mHistoryContent;
	private static int stepcount;
	
	/**
	 * This is the constructor for this class
	 * Creates a JTeaxtArea to show the move history, and creates a JScrollPane to put that JTextArea in
	 * Then adds the JScrollPane to the JPanel
	 */
	public MoveHistory()
	{
		/**
		 * Creates and fills up the textarea and sets the background color
		 */
        mHistoryTextArea = new JTextArea(mHistoryContent);
        mHistoryTextArea.setBackground(Color.decode("#289696"));
        /**
         * Creates the scrollpane and sets its border, backround, and font color up, fills it with the textarea and sets the size of it
         */
        mHistoryScrollPane = new JScrollPane(mHistoryTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mHistoryScrollPane.setBorder(BorderFactory.createTitledBorder("Move History"));
        mHistoryScrollPane.setBackground(Color.decode("#289696"));
        mHistoryScrollPane.setForeground(Color.decode("#285f5f"));
        mHistoryScrollPane.setViewportView(mHistoryTextArea);
        mHistoryScrollPane.setPreferredSize(new Dimension(300, 390));
        /**
         * here it adds the mHistoryScrollPane to the Panel
         */
        this.add(mHistoryScrollPane);
	}

	/**
	 * This method prints the chess figure's move to the JPanel through the text area
	 * @param selected is chess figure which was moved on the table
	 * @param oldPos this is the selected old position
	 * @param newPos this is the selected new position
	 * @param captures this is a boolean if it's true the moved figure captures a figure from the other color
	 * When the new string is done, it overrides the text area to add it to its own value
	 */
	public static void printMoveHistory(Chess_piece selected, Position oldPos, Position newPos, boolean captures, String captured, boolean check)
	{
		String newMoveEntry = stepcount + ".  " + selected.getColor().getString() + " " + selected.getType() + ":  ";
		newMoveEntry += oldPos.getXString() + oldPos.getYString() + " -> ";
		newMoveEntry += newPos.getXString() + newPos.getYString() + "  ";
		if(captures)
			newMoveEntry += "captures(" + captured + ")";
		if(check)
			newMoveEntry += "  check";
		newMoveEntry += "\n";
		
		mHistoryContent += newMoveEntry;
		
		SwingUtilities.invokeLater(new Runnable()
		{
            @Override
            public void run()
            {
                mHistoryTextArea.setText(mHistoryContent);
            }
        });
		stepcount++;
	}
	
	/**
	 * This method provides the value of its string variable to outer classes 
	 * @return the current state of the mHistoryContent string
	 */
	public static String getMoveHistory()
	{
		return mHistoryContent;
	}
	
	/**
	 * This method provides the value of its counting variable to outer classes
	 * @return gives the step count out
	 */
	public static int getEnd()
	{
		return stepcount;
	}
	
	/**
	 * This method sets up the variables with values from the parameters
	 * @param mhistory is the string for the text area
	 * At the end sets the text area up with the mhistory string
	 */
	public void setMoveHistory(String mhistory)
	{
		mHistoryContent = mhistory;
		stepcount = Board.getStep() + 1;
		
		mHistoryTextArea.setText(mHistoryContent);
	}
}