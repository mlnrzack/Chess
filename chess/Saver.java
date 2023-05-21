package chess;

/**
 * This saves the chess board
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import chess.pieces.Chess_piece;

public class Saver
{
	private static Chess_piece[][] board;
	private static SideColor[][] color = new SideColor[8][8];
	private static boolean whiteMove = true;
	private static int steps = 0;
	
	/**
	 * This method is called by other classes to starts the saving of the game
	 * This method gets its values from the @link Board
	 * @param save is the current state of the board, gets all figures and types with this one
	 * @param whitemove this is to correctly states whether white moved or black moved
	 * Sets its own values by the parameters
	 */
	public static void toSaver(Chess_piece[][] save, boolean whitemove, int stepcount)
	{
		/**
		 * gets the whole board
		 */
		board = save;
		/**
		 * gets the color of the figures
		 * if the isn't any figure on a position then it's going to set it to NOCHESSPIECE
		 */
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(save[j][i] != null)
					color[j][i] = save[j][i].getColor();
				else
					color[j][i] = SideColor.NOCHESSPIECE;
			}
		}
		/**
		 * sets who moves next, and the step count
		 */
		whiteMove = whitemove;
		steps = stepcount;
	}
	
	/**
	 * This method is called by other classes to following up the saving of the game
	 * @param file is the chosen file by the user
	 * @param mode is the game mode for the game, pvp or ai
	 * Calls 2 other method which executes the saving progress
	 */
    public static void saveGame(File file, String mode) 
    {   
    	saveBoard(file, mode);
        saveMoveHistory(file, MoveHistory.getMoveHistory());
    }

    /**
     * This method saves the board
     * @param file the file to write in
     * @param mode for what type of game was this
     * Tries to write these values to the file if it was successful it opens a message dialog
     */
    private static void saveBoard(File file, String mode)
    {
    	try 
    	{
    		/**
    		 * opens the file to write
    		 */
    		FileOutputStream fos = new FileOutputStream(file);
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		/**
    		 * writes the file with the values
    		 */
    		oos.writeObject(mode);
    		oos.writeObject(board);
    		oos.writeObject(color);
    		oos.writeObject(whiteMove);
    		oos.writeObject(steps);
    		/**
    		 * closes the file after write
    		 */
    		oos.close();
    		fos.close();
    		/**
    		 * shows the message dialog that the save was successful
    		 */
    		JOptionPane.showMessageDialog(null, "Saved the gamestanding!", "Save successful", JOptionPane.INFORMATION_MESSAGE);
    	}
    	/**
    	 * when it can't save the file
    	 */
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
   /**
    * This method saves the move history
    * @param file the file to write in
    * @param print the move history
    * Tries to write these values to the file
    */
    private static void saveMoveHistory(File file, String print)
    {
    	try 
    	{
    		/**
    		 * Reroutes the file's path, so the user has to only choose one file
    		 */
    		String newpath = file.getAbsolutePath().toString().split("[.]")[0] + "mh." + file.getAbsolutePath().toString().split("[.]")[1];
    		/**
    		 * Opens the file to write
    		 */
    		FileOutputStream fos = new FileOutputStream(new File(newpath));
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		/**
    		 * Writes the file
    		 */
    		oos.writeObject(print);
    		/**
    		 * Closes the file
    		 */
    		oos.close();    		
    		fos.close();
    	}
    	/**
    	 * catches if there is any error
    	 */
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}