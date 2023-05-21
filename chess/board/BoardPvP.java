package chess.board;

/**
 * Based on Forestf90 GamePanelHot.java
 * This creates the chess game for the PvP game mode
 * Modified it to be able to load a saved game
 */

import chess.pieces.Chess_piece;
import chess.SideColor;

public class BoardPvP extends Board
{
	/**
	 * Creates a new board
	 * Calls the super's constructor
	 */
	public BoardPvP() {}
	
	/**
	 * Creates a board with a saved game standing with machine controlled enemy
	 * @param load to be loaded to board
	 * @param color to be loaded to board
	 * @param whitemv to be loaded to board, it determines who is going to move, when the game starts
	 * @param stepc is the step count
	 * the load and color parameters are for the board, they contains all of standing's needed information, which figure is where, and which figure has what color
	 * calls the super's constructor with the @param
	 */	
	public BoardPvP(Chess_piece[][] load, SideColor[][] color, boolean whitemv, int stepc)
	{
		super(load, color, whitemv, stepc);
	}
	
	/**
	 * Determines whose turn will be
	 */
	@Override
	void oponentTurn() 
	{
		/**
		 * XORs the whiteMove with true every time
		 */
		whiteMove ^= true;		
	}
}