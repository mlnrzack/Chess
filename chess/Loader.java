package chess;

/**
 * This class is for the loading mechanism
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import chess.pieces.Chess_piece;

public class Loader 
{
	private static String mode = "";
	private static Chess_piece[][] board = new Chess_piece[8][8];
	private static SideColor[][] color = new SideColor[8][8]; 
	private static boolean whitemv = true;
	private static String mh = "";
	private static int steps = 0;
	
	/**
	 * This method calls the 2 loaders
	 * @param file is the file what the user chose to open
	 */
	public static void loadGame(File file)
	{
		loadBoard(file);
		loadHistory(file);
	}
	
	/**
	 * This method tries to load the board of the game
	 * @param file is the file what the user chose to open
	 */
	private static void loadBoard(File file)
	{
		try 
    	{
			/**
			 * creates the necessaries to open and read the file
			 */
    		FileInputStream fis = new FileInputStream(file);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		
    		mode = (String) ois.readObject();
    		board = (Chess_piece[][]) ois.readObject();
    		color = (SideColor[][]) ois.readObject();
    		whitemv = (boolean) ois.readObject();
    		steps = (int) ois.readObject();
    		
    		ois.close();
    		fis.close();
    	}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method tries to load the move history of the game
	 * @param file is the file what the user chose to open
	 */
	private static void loadHistory(File file)
	{
		try
		{
			/**
			 * here it gets a new path for the right file
			 */
			String newpath = file.getAbsolutePath().toString().split("[.]")[0] + "mh." + file.getAbsolutePath().toString().split("[.]")[1];
			/**
			 * creates the necessaries to open and read the file
			 */
			FileInputStream fis = new FileInputStream(new File(newpath));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			mh = (String) ois.readObject();
			
			ois.close();
			fis.close();
		}
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method gives the mode to an outer class, so it can decide how to call the board
	 * @return value is either pvp or ai
	 */
	public static String getMode()
	{
		return mode;
	}
	
	/**
	 * This method gives which color is the next to move to an outer class
	 * @return value contains that answer
	 */
	public static boolean getWhite()
	{
		return whitemv;
	}

	/**
	 *  This method gives the move history to an outer class
	 * @return is the whole move history in string
	 */
	public static String getMh()
	{
		return mh;
	}

	 /** 
	 * This method gives the last move's number, when the game was saved, to an outer class
	 * @return is the valoue of that
	 */
	public static int getSteps()
	{
		return steps;
	}

	/**
	 * This method gives the Chess_piece of a given index to an outer class
	 * @param i is the i-th column of the board
	 * @param j is the j-th row of the board
	 * @return i is the value from the board 2 dimension array
	 */
	public static Chess_piece getBoard(int j, int i)
	{
		return board[j][i];
	}

	/**
	 * This method gives the SideColor of a given index to an outer class
	 * @param i is the i-th column of the board
	 * @param j is the j-th row of the board
	 * @return is the value from the color 2 dimension array
	 */
	public static SideColor getColor(int j, int i)
	{
		return color[j][i];
	}
}